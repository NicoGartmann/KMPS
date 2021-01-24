package main

import (
	"encoding/json"
	"fmt"
	"log"
	"net/http"

	"github.com/gorilla/websocket"
	"strconv"
	"bufio"
	"os"
)

type ClientName struct {
	ClientID string
	Zuweisung string
}


var clients []ClientName
var tickets []string
var hub = newHub()


func reader(conn *websocket.Conn) {
	for {
		// read in a message
		var data ClientName
		messageType, p, err := conn.ReadMessage()
		if err != nil {
			log.Println(err)
			return
		}
		
		err1 := json.Unmarshal(p, &data)
		if err1 != nil {
			panic(err1)
		}
		
		if (data.Zuweisung == "neu") {
			//neuen client in array pushen
			clients = append(clients, ClientName{"client" + data.ClientID, "Keine Zuweisung"})
			fmt.Println("Neuer Client: client" + data.ClientID)
			if(len(tickets) == 0) {
				fmt.Println("Keine Tickets")
			}else{
				fmt.Println("Tickets:")
				for i := 0; i < len(tickets); i++ {
					fmt.Println(tickets[i])
				}
			}
			fmt.Println("n: neues Ticket q: Quit")
			fmt.Println()
			fmt.Println()
			jsonTickets, err := json.Marshal(tickets)
			if err != nil {
				panic(err)
			}
			if err := conn.WriteMessage(messageType, []byte(jsonTickets)); err != nil {
				log.Println(err)
				return
			}
		}else{
			//neue Zuweisung vom Client erhalten
			
			index, err := strconv.Atoi(data.Zuweisung)
			if err != nil {
				panic(err)
			}
			tickets[index-1] = "ticket" + strconv.Itoa(index) + " (client" + data.ClientID + ")"
			if(len(tickets) == 0) {
				fmt.Println("Keine Tickets")
			}else{
				fmt.Println("Tickets:")
				for i := 0; i < len(tickets); i++ {
					fmt.Println(tickets[i])
				}
			}
			fmt.Println("n: neues Ticket q: Quit")
			fmt.Println()
			fmt.Println()
			jsonTickets, err := json.Marshal(tickets)
			if err != nil {
				panic(err)
			}
			hub.broadcast <- jsonTickets

		}
	}
}

func addTicket(hub *Hub) {
	for{
		reader := bufio.NewReader(os.Stdin)
		char, _, err := reader.ReadRune()

		if err != nil {
		fmt.Println(err)
		}
		if (char == 'n') {
			i := len(tickets)
			tickets = append(tickets, "ticket" + strconv.Itoa(i+1) + " (nicht zugewiesen)")
			if(len(tickets) == 0) {
				fmt.Println("Keine Tickets")
			}else{
				fmt.Println("Tickets:")
				for i := 0; i < len(tickets); i++ {
					fmt.Println(tickets[i])
				}
			}
			fmt.Println("n: neues Ticket q: Quit")
			fmt.Println()
			fmt.Println()
			jsonTickets, err := json.Marshal(tickets)
			if err != nil {
				panic(err)
			}
			hub.broadcast <- jsonTickets
		}else if(char == 'q') { os.Exit(1)}
	}
}



func main() {
	fmt.Println("Server gestartet")
	fmt.Println("Keine Tickets")
	fmt.Println("n: neues Ticket q: Quit")
	fmt.Println()
	fmt.Println()
	go hub.run()
	go addTicket(hub)
	http.HandleFunc("/ws", func(w http.ResponseWriter, r *http.Request) {
        serveWs(hub, w, r)
    })
	log.Fatal(http.ListenAndServe(":8080", nil))
}
