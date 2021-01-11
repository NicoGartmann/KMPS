const ws = require('ws');
const input = require('readline');

const port = 3000; 
const server = new ws.Server({ port });

const clients = new Array(); 

var rl = input.createInterface({
  input: process.stdin,
  output: process.stdout
});

console.log("Server gestartet.\nKeine Tickets.");
rl.question("n: neues Ticket, q: Quit\n", function(answer) {
  if( answer == 'n' ) {
      
  } else if( answer == 'q') {
    process.exit(); 
  }
}); 

server.on('connection', function (new_client) {
  console.log('neuer Client: ', new_client)
  clients.push(JSON.parse(new_client.data)); 
  console.log(clients); 
}); 



