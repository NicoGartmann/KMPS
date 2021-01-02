const net = require('net');
const readline = require('readline')
const fs = require('fs');

const server = net.createServer((socket) => {
    socket.on('data', (data) => {
        console.log('Neuer Client: client',data.toString(),'\nKeine Tickets.\nn:neues Ticket, q:Quit.\n');
    });
}).on('error', (err) => {
    console.error(err);
});

// Open server on port 9898
server.listen(9898, () => {
    console.log('Server gestartet.\n');
    
    //Hole die Tickets
    fs.readFile('./tickets.json', 'utf8', (err, jsonString) => {
        const tickets = JSON.parse(jsonString);
         
        //es existieren Tickets
        if(tickets.tickets.length >= 1) {
            console.log('Tickets:'); 
            for(let i=0; i<tickets.tickets.length; i++) {
                if(tickets.tickets[i].zugewiesen) {
                    console.log(tickets.tickets[i].name,'(',tickets.tickets[i].zugewiesen,')');
                } else {
                    console.log(tickets.tickets[i].name,'(nicht zugewiesen)');
                }
            }
        } else {
            console.log('Keine Tickets\n'); 
        } 

        //Consolen-Reader
        const rl = readline.createInterface({
            input: process.stdin,
            output: process.stdout
          });
        rl.question('n:neues Ticket, q:Quit:', (answer) => {
            if(answer == 'n') {
                
            } else if(answer == 'q'){
                console.log('quit'); 
            }
            rl.repeat(); 
        });
    })

});