package main

import (
	"fmt"
	"log"
	"net/http"
	"strings"

	"github.com/gorilla/websocket"
)

var broadcast = make(chan Ticket)

func reader(conn *websocket.Conn) {
	for {
		_, p, err := conn.ReadMessage()
		if err != nil {
			return
		}

		if strings.HasPrefix(string(p), "client") {
			log.Println("Neuer Client: ", string(p))
			conn.WriteMessage()
		} else {

		}

	}
}

func newTicket() {

}

var upgrader = websocket.Upgrader{
	CheckOrigin: func(r *http.Request) bool {
		return true
	},
}

func wsEndpoint(w http.ResponseWriter, r *http.Request) {
	ws, err := upgrader.Upgrade(w, r, nil)

	if err != nil {
		return
	}

	reader(ws)

	newTicket(ws)
}

func main() {
	fmt.Println("Server gestartet.\nKeine Tickets.\nn: neues Ticket, q: Quit")

	http.HandleFunc("/", wsEndpoint)

	http.ListenAndServe(":3000", nil)
}
