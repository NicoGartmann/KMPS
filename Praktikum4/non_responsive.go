package main

import (
	"fmt"
	"time"

	"fyne.io/fyne/app"
	"fyne.io/fyne/widget"
)

func calc() int {
	time.Sleep(10 * time.Second)
	return 42
}

func main() {
	app := app.New()

	w := app.NewWindow("Non Responsive")

	content := widget.NewButton("calc", func() {
		fmt.Println("Button gedrueckt")
		fmt.Println(calc())
	})

	w.SetContent(content)
	w.ShowAndRun()
}
