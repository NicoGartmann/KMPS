const WebSocket = require('ws');
const input = require('readline');
const socket = new WebSocket.Server({ port:3000});
function Ticket(id, name, zugewiesen) {
  this.id = id; 
  this.name = name, 
  this.zugewiesen = zugewiesen; 
}
let tickets = new Array(); 
let eingabe_string = "n: neuer Eintrag, q: Quit\n"; 


// Server Info Output
console.log("Server gestartet.\nKeine Tickets.");
rl.question(eingabe_string, (answer) => {
  validate(answer); 
} );  

socket.on('connection', function(ws) {

  ws.on('message', function incoming(data) {
    data = JSON.parse(data); 
    if(data.startsWith('client')) {
      // Client Anmeldung
      ws.send(JSON.stringify(tickets));

      console.log(); 
      console.log(`Neuer Client: ${data}`);
      printTickets(); 
      console.log(eingabe_string);
    } else {
      // Ticket zuweisen
      let ticket_info = data.split(',');
      if(tickets[ticket_info[0]]) {
        tickets[ticket_info[0]].zugewiesen = ticket_info[1]; 
      }

      // Clients informieren
      socket.clients.forEach(function each(client) {
        client.send(JSON.stringify(tickets)); 
      })

      console.log(); 
      printTickets(); 
      console.log(eingabe_string); 
    }

  }); 
}); 

var rl = input.createInterface({
  input: process.stdin,
  output: process.stdout
});

function newTicket() {
  // Ticket anlegen und speichern
  var new_ticket = new Ticket(tickets.length,'ticket'.concat(tickets.length),'nicht zugewiesen'); 
  tickets.push(new_ticket); 

  // Clients informieren
  socket.clients.forEach(function each(client) {
    client.send(JSON.stringify(tickets)); 
  }); 

  printTickets(); 

  rl.question(eingabe_string, (answer) => {
    validate(answer); 
  });
}

function printTickets() {
  if(tickets.length > 0 ) {
    console.log('Tickets:'); 
    tickets.forEach(ticket => {
      console.log(ticket.name,`(${ticket.zugewiesen})`); 
    });
  } else {
    console.log('Keine Tickets'); 
  }
}

// Eingabe auswerten
function validate(answer) {
  console.log(); 
  if( answer == 'n' ) {
    // Ticket erstellen
    newTicket();
  } else if( answer == 'q') {
    // Beenden
    rl.close(); 
    process.exit(); 
  }
}


