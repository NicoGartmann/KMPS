package main

import (
	"fmt"
	"time"

	"fyne.io/fyne/app"
	"fyne.io/fyne/widget"
)

func calc(my_channel chan int) {
	time.Sleep(10 * time.Second)
	my_channel <- 42
}

func print(my_channel chan int) {
	fmt.Println(<-my_channel)
}

func main() {
	my_channel := make(chan int)
	app := app.New()

	w := app.NewWindow("Non Responsive")

	content := widget.NewButton("calc", func() {
		fmt.Println("Button gedrueckt")
		go calc(my_channel)
		go print(my_channel)
	})

	w.SetContent(content)
	w.ShowAndRun()
}
