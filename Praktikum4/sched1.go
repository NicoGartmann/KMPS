package main

import (
	"fmt"
	"os"
	"runtime"
	"runtime/trace"
	"time"
)

func main() {
	f, err := os.Create("trace.out")
	if err != nil {
		panic(err)
	}
	defer f.Close()
	err = trace.Start(f)
	if err != nil {
		panic(err)
	}
	defer trace.Stop()
	runtime.GOMAXPROCS(1) // ... limitiere die nutzbaren Prozessoren auf einen.
	fmt.Printf("runtime.GOMAXPROCS(0) returned %d CPUs\n", runtime.GOMAXPROCS(0))

	go func(my_name string) {
		for i := 0; i < 10; i++ {
			fmt.Println(my_name, ": ", i)
		}
	}("goroutine1")
	go func(my_name string) {
		for i := 0; i < 10; i++ {
			fmt.Println(my_name, ": ", i)
		}
	}("goroutine2")
	go func(my_name string) {
		for i := 0; i < 10; i++ {
			fmt.Println(my_name, ": ", i)
		}
	}("goroutine3")

	time.Sleep(10 * time.Second)
}
