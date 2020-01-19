package main

import (
	"fmt"
	"testing"
)

func TestRecover(t *testing.T) {
	defer func() {
		if err := recover(); err != nil {
			fmt.Println("Got error:")
			fmt.Println(err)
		}
	}()

	var c interface{} = []int{10}
	var d interface{} = []int{20}
	fmt.Println(c == d)
}
