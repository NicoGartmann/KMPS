const WebSocket = require('ws'); 

const socket = new WebSocket('ws://localhost:8080'); 


/*


socket.addEventListener('open', function (event) { 
    socket.send('Hallo Server!'); 
}); 

socket.addEventListener('message', function (event) { 
    console.log('Nachricht vom Server ', event.data); 
});

*/