package main

import (
	"fmt"
	"time"
)

func start_goroutine() {
	var d int = 5

	go func(dp int) {
		fmt.Println("Now sleeping for", dp, "seconds ...")
		time.Sleep(time.Duration(d) * time.Second)
		fmt.Println("Goroutine ends after ", dp, "seconds.")
	}(d)
}

func main() {
	start_goroutine()

	time.Sleep(20 * time.Second)
}
