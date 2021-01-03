const WebSocket = require('ws');
var readline = require('readline');

const port = 6969;
const wss = new WebSocket.Server({ port });
var clients = new Array();
var tickets = new Array();
var alloc = new Array();
var rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

console.log("Server gestartet");
console.log("Keine Tickets");
rl.question("n: neues Ticket. q: quit ", function(answer) {
  if(answer == `n`) {
    newTicket();
  }
  if(answer == `q`) {
    process.exit();
  }
});
console.log();
console.log();


wss.on('connection', function connection(ws) {
  ws.on('message', function incoming(data) {
    var message = JSON.parse(data);
    if(message[0].startsWith("client")) {
      printNewClient(JSON.parse(data), ws);
    }else{
      setTicket(parseInt(JSON.parse(data)[0])-1, JSON.parse(data)[1]);
    }
  })
})


function printNewClient(data, socket) {
  clients.push(data);
  console.log(`Neuer Client: ${data}`);
  
  if(tickets.length == 0) {
    console.log(`Keine Tickets`);
  }else{
    tickets.forEach(ticket => {
      console.log(ticket);
    });
  }

  socket.send(JSON.stringify(tickets));
  
  rl.question("n: neues Ticket. q: quit ", function(answer) {
    if(answer == `n`) {
      newTicket();
    }
    if(answer == `q`) {
      process.exit();
    }
  });
  console.log();
  console.log();
  
}

function newTicket() {
  tickets.push("ticket" + (tickets.length+1).toString() + " (nicht zugewiesen)");
  alloc.push("nope");
  console.log("Tickets:");
  tickets.forEach(ticket => {
    console.log(ticket);
  });
  broadcastTickets();
  rl.question("n: neues Ticket. q: quit ", function(answer) {
    if(answer == `n`) {
      newTicket();
    }
    if(answer == `q`) {
      process.exit();
    }
  });
  console.log();
  console.log();
}

function setTicket(index, cli) {
  if(alloc[index] == "nope") {
    alloc[index] = cli;
    tickets[index] = "ticket" + (index+1).toString() + " (" + cli + ")";
  }
  console.log("Tickets:");
  tickets.forEach(ticket => {
    console.log(ticket);
  });
  rl.question("n: neues Ticket. q: quit ", function(answer) {
    if(answer == `n`) {
      newTicket();
    }
    if(answer == `q`) {
      process.exit();
    }
  });
  console.log();
  console.log();
  broadcastTickets();
}

function broadcastTickets() {
  wss.clients.forEach(function each(client) {
    client.send(JSON.stringify(tickets));
 });
}