const ws = require('ws'); 
const prompt = require('prompt-sync')();
const input = require('readline'); 

var rl = input.createInterface({
  input: process.stdin,
  output: process.stdout
});
 
console.log('Client gestartet.'); 
const new_client = prompt('Bitte Client-ID eingeben: ');

connection = new ws('ws://localhost:3000'); 

connection.onopen = function() {
  console.log('Verbindung hergestellt.\n'); 
  connection.send(JSON.stringify("Hello Server"));
}

connection.onclose = function () {
  console.log('Verbindung verloren.\n'); 
  connection.close();
}
