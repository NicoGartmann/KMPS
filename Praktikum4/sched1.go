package main

import (
	"fmt"
	"runtime"
	"time"
)

func f(my_name string) {
	for i := 0; i < 10; i++ {
		fmt.Println(my_name, ": ", i)
	}
}

func main() {
	runtime.GOMAXPROCS(1)
	fmt.Printf("runtime.GOMAXPROCS(0) retuned %d CPUs\n", runtime.GOMAXPROCS(0))

	go f("goroutine1")
	go f("goroutine2")
	go f("goroutine3")

	time.Sleep(10 * time.Second)
}
