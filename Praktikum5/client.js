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
let ws;
if (ws) {
  ws.onerror = ws.onopen = ws.onclose = null;
  ws.close();
}

ws = new WebSocket('ws://localhost:3000');
ws.onopen = () => {
  var nameArr = Array();
  nameArr.push(name);
  ws.send(JSON.stringify(nameArr));
}
ws.onclose = function() {
  ws = null;
}
ws.on('message', function incoming(data) {
    tickets = JSON.parse(data);
    
    showTickets(tickets);
})

function showTickets(data) {
  
  if( data.length == 0 ) {
    console.log(`Client: ${name}`);
    console.log("Keine Tickets");
  } else {
    console.log(`Tickets:`);
    data.forEach(dat => {
      console.log(dat);
    });
  }
  rl.question("number Selbstzuw. q: quit ", function(answer) {
    if(answer == `q`) {
      process.exit();
    }else{
      sendAlloc(answer);
    }
  });

  console.log();
  console.log();
}

function sendAlloc(i) {

  if(i <= tickets.length && tickets[i-1].indexOf("client") == -1) {
    var arr = Array();
    arr.push(i);
    arr.push(name);
    ws.send(JSON.stringify(arr));
  } else {

    if(i > tickets.length) {
      console.log("Ticket existiert nicht!");
    }else{
      console.log("Ticket ist bereits vergeben!");
    }

    rl.question("number Selbstzuw. q: quit ", function(answer) {
      if(answer == `q`) {
        process.exit();
      }else{
        sendAlloc(answer);
      }
    });

    console.log();
    console.log();
  }
}