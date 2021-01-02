const net = require('net');
const readline = require('readline')

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

console.log('Client gestartet'); 

var response = rl.question('Bitte Client-ID eingeben: ', answer);

function answer(response) {
    console.log('Client: client', response); 
    console.log('Keine Tickets.\n');

    const client = net.createConnection({ port: 9898 }, () => {
        client.write(response); //id des clients übergeben
        client.end(); 
    });
}

client.on('data', (data) => {
    console.log(data.toString()); 
    client.end(); 
})



response = rl.question('number:Selbstzuw. q:Quit: ', command); 

function command(response) {
    console.log(response); 
}




