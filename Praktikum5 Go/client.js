const WebSocket = require('ws');
const prompt = require('prompt-sync')();
var readline = require('readline');

var rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
});

var tickets;
console.log("Client gestartet");
const name = prompt('Bitte Client-ID eingeben: ');
console.log();
console.log();

let socket = new WebSocket('ws://localhost:8080/ws');

socket.onopen = () => {
    
    socket.send(JSON.stringify({
        "ClientID": name,
        "Zuweisung": "neu"
    }))
};

function zuweisen(x) {
    if(x <= tickets.length && tickets[x-1].includes("nicht zugewiesen")) {
        socket.send(JSON.stringify({
            "ClientID": name,
            "Zuweisung": x.toString()
        }))
    }else if(x > tickets.length) {
        console.log("Ticket existiert nicht!");
    }else{
        console.log("Ticket ist bereits vergeben!");
    }
    rl.question("number Selbstzuw. q: Quit ", function(answer) {
        if(answer == `q`) {
          process.exit();
        }else{
          zuweisen(answer);
        }
    
      });
    console.log();
    console.log();
}
        
socket.onclose = event => {
    console.log("Socket Closed Connection: ", event);
    
};

socket.onmessage = function(event) {
    
    if(event.data == "null") {
        console.log("Client: " + "client" + name);
        console.log("Keine Tickets");
    }else{
        tickets = JSON.parse(event.data);
        console.log("Tickets:");
        for(i=0; i < tickets.length; i++) {
            console.log(tickets[i]);
        }
    }
    rl.question("number Selbstzuw. q: Quit ", function(answer) {
        if(answer == `q`) {
          process.exit();
        }else{
          zuweisen(answer);
        }
    
      });
    console.log();
    console.log();
    
}

socket.onerror = error => {
    console.log("Socket Error: ", error);
};