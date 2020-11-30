package main

import (
	"fmt"
	"time"
)

func main() {
	for i := 0; i < 9; i++ {
		go func() {
			fmt.Println("Wert von i:\n", i)
			time.Sleep(2 * time.Second)
			fmt.Println("Addresse : \n", &i)
		}()
	}

	time.Sleep(10 * time.Second)

	fmt.Println("Done.")
}
