package main

import (
	"fmt"
	"testing"
)

func counter(out chan<- int) {
	out <- 1
	close(out)
}

func compute(out chan<- int, in <-chan int) {
	for v := range in {
		out <- v
	}
	close(out)
}

func TestSingleChannel(t *testing.T) {
	naturals := make(chan int)
	squares := make(chan int)
	go counter(naturals)
	go compute(squares, naturals)
	for v := range squares {
		fmt.Println(v)
	}
}
