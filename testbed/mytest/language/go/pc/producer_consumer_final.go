package main

import (
	"fmt"
	"sync"
)

func produce(ch chan<- int) <-chan struct{} {
	done := make(chan struct{})
	go func() {
		for i := 0; i < 100; i++ {
			ch <- i
		}
		close(done)
	}()
	return done
}

func consume(wg *sync.WaitGroup, ch <-chan int) {
	defer wg.Done()
	for i := range ch {
		fmt.Printf("Consumer: %d\n", i)
	}
}

func main() {
	ch := make(chan int, 1)
	wg := sync.WaitGroup{}
	done := produce(ch)
	wg.Add(1)
	go consume(&wg, ch)
	<- done // produce done
	close(ch)
	wg.Wait()
	// consumer done
	fmt.Println("All done, exit")
}
// A hybrid would be the easiest solution at this point: Add the consumer to a waitgroup, and use the done channel in the producer to get:
