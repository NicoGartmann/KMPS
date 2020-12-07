package main

import (
	"os"
	"runtime/trace"
)

func main() {
	trace.Start(os.Stderr)
	defer trace.Stop()

	ch := make(chan int)

	go func() {
		ch <- 42
	}()

	<-ch
}
