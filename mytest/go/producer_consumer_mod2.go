package main

import (
	"sync"
)

func product(wg *sync.WaitGroup, ch chan<- int) {
	defer wg.Done() // signal we've done our job
	for i := 0; i < 100; i++ {
		ch <- i
	}
}

func main() {
	ch := make(chan int, 1)
	wg := sync.WaitGroup{}
	wg.Add(1) // I'm adding a routine to the channel
	go produce(&wg, ch)
	wg.Wait() // will return once `produce` has finished
	close(ch)
}