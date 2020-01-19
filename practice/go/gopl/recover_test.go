package main

import (
	"fmt"
	"os"
	"testing"
)

func check() {
	if err := recover(); err != nil {
		fmt.Println("Got error")
		fmt.Println(err)
	}
}

func TestRecover(t *testing.T) {
	defer check()
	var c interface{} = []int{10}
	var d interface{} = []int{20}
	fmt.Println(c == d)
}

// my comments
func TestTypeAssertion(t *testing.T) {
	defer check()
	//v := os.Stdout
	//v2 = v.(io.readWriter)
	// go internal package
	// go map value *=
	// go module
	// go flag
	// tcp Dial
	
}
