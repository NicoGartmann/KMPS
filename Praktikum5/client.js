const WebSocket = require('ws')
const prompt = require('prompt-sync')()
const input = require('readline')
var rl = input.createInterface({
  input: process.stdin,
  output: process.stdout
});

console.log('Client gestartet.'); 

const client_name = prompt('Bitte Client ID eingeben: '); 
let socket = new WebSocket('ws://localhost:3000'); 
let eingabe_string = 'number: Selbstzuw. q: Quit\n'; 
let tickets = new Array(); 

socket.onopen =  function connection(e) {
  // Server ueber Anmeldung informieren
  socket.send(JSON.stringify(client_name));  
  console.log(`Client: ${client_name}`); 

}; 

socket.onmessage = function message(event) {
  tickets = JSON.parse(event.data); 
  // Tickets ausgeben
  if(tickets.length > 0) {
    console.log(); 
    console.log('Tickets:');
    tickets.forEach(ticket => {
      console.log(`${ticket.id}:`,ticket.name,`(${ticket.zugewiesen})`); 
    })
    console.log(); 
    rl.question(eingabe_string, (answer) => {
      validate(answer); 
    })
  } else {
    console.log('Keine Tickets'); 
    rl.question(eingabe_string, (answer) => {
      validate(answer); 
    })
  }
}

function validate(answer) {
  if(answer == 'q') {
    // Beenden
    process.exit(); 
  } else {
    // Ticket ID und Client Name an Server senden
    socket.send(JSON.stringify(answer.concat(`,${client_name}`))); 
  }
}








