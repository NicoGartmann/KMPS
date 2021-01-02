const net = require('net');
const readline = require('readline')
const fs = require('fs')

fs.readFile('./tickets.json', (err, jsonString) => {
    if (err) {
        console.log("File read failed:", err)
        return
    }
    try {
        const tickets = JSON.parse(jsonString); 
        tickets.foreach(ticket => console.log(ticket)); 
        
    } catch(err) {
        console.log('Error parsing JSON string:', err)
    }
}); 

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

const server = net.createServer((socket) => {
    socket.on('data', (data) => {
        console.log('Neuer Client: client',data.toString(),'\nKeine Tickets.\nn:neues Ticket, q:Quit.\n');
    });
}).on('error', (err) => {
    console.error(err);
});

// Open server on port 9898
server.listen(9898, () => {
    console.log('Server gestartet.\nKeine Tickets.\nn:neues Ticket, q:Quit.\n');
});