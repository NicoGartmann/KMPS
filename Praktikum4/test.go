package main 

import (
	"fmt"
	"runtime"
)

func main() {
	fmt.Printf("runtime.GOMAXPROCS(-1) returned %d CPUS\n", runtime.GOMAXPROCS(-1))
	fmt.Printf("runtime.NumCPU() returned %d CPUS\n", runtime.NumCPU())
}