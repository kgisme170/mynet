package main

import (
	"fmt"
	"testing"
)

func closure() func() int {
	var x int
	return func() int {
		x++
		return x
	}
}

func TestClosure(t *testing.T) {
	c := closure()
	fmt.Println(c())
	fmt.Println(c())
	fmt.Println(c())
}
