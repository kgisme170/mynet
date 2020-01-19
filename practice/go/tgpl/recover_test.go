package main

import (
	"fmt"
)

func TestRecover() {
	var c interface{} = []int{10}
	var d interface{} = []int{20}
	fmt.Println(c == d)
}
