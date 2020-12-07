package main

import (
	"fmt"
	"time"
)

func start_goroutine() {
	var d int = 5

	go func(dp int) {
		fmt.Println("Now sleeping for", dp, "seconds ...")
<<<<<<< HEAD
		time.Sleep(time.Duration(d) * time.Second)
=======
		time.Sleep(time.Duration(dp) * time.Second)
>>>>>>> 12a88b0bd8024a960c655826ee61f6dc70143263
		fmt.Println("Goroutine ends after ", dp, "seconds.")
	}(d)
}

func main() {
	start_goroutine()

	time.Sleep(20 * time.Second)
}
