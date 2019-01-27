package main

import "fmt"

// using a done channel
func produce(ch chan<- int) <-chan struct{} {
	done := make(chan struct{})
	go func() {
		for i := 0; i < 100; i++ {
			ch <- i
		}
		// all values have been published
		// close done channel
		close(done)
	}()
	return done
}

func main() {
	ch := make(chan int, 1)
	done := produce(ch)
	go consume(ch)
	<-done // if producer has done its thing
	close(ch) // we can close the channel
}

func consume(ch <-chan int) {
	// we can now simply loop over the channel until it's closed
	for i := range ch {
		fmt.Printf("Consumed %d\n", i)
	}
}
// OK, but here you'll still need to wait for the consume routine to complete.
// You may have already noticed that the done channel technically isn't closed in the same routine that creates it either. Because the routine is defined as a closure, however, this is an acceptable compromise. Now let's see how we could use a waitgroup:
