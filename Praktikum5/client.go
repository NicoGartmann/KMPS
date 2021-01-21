package main

import (
	"flag"
	"log"
	"net/url"

	"github.com/gorilla/websocket"
)

var addr = flag.String("addr", "localhost:3000", "http service address")

func main() {
	u := url.URL{
		Scheme: "ws:",
		Host:   *addr,
		Path:   "/",
	}

	c, _, err := websocket.DefaultDialer.Dial(u.String(), nil)
	if err != nil {
		log.Println("DefaultDialer failed")
	}

	err = c.WriteMessage(websocket.TextMessage, []byte("Hallo Server"))
	if err != nil {
		log.Println("WriteMessage failed")
	}

}
