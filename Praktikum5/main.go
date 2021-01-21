package main

import (
	"log"
	"net/http"

	"github.com/gorilla/websocket"
)

var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

var clients = make(map[*websocket.Conn]bool)
var broadcast = make(chan Ticket)

type Ticket struct {
	Id         string `json:"id"`
	Name       string `json:"name"`
	zugewiesen string `json:"zugewiesen"`
}

func main() {

	// WebSocket Route
	http.HandleFunc("/", handleConnections)

	// auf Nachrichten hoeren
	go handleMessages()

	// Server starten
	http.ListenAndServe(":3000", nil)
}

func handleConnections(w http.ResponseWriter, r *http.Request) {
	ws, err := upgrader.Upgrade(w, r, nil)
	if err != nil {
		log.Fatal(err)
	}

	// Neuen Client registrieren
	clients[ws] = true

	for {
		var msg string

		err := ws.ReadJSON(&msg)
		if err != nil {
			delete(clients, ws)
			break
		}
		if(string.HasPrefix(msg, "client")) {
			log.Printf("Neuer Client: ", msg)
		} else {
			var ticket := Ticket{zugewiesen}
		}


		broadcast <- ticket
	}
}

func handleMessages() {
	for {
		ticket := <-broadcast

		for client := range clients {
			err := client.WriteJSON(ticket)
			if err != nil {
				log.Printf("error: %v", err)
				client.Close()
				delete(clients, client)
			}
		}
	}
}
