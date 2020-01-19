package main

import (
	"fmt"
	"testing"
	"unsafe"
)

func TestUseRawPointer(t *testing.T) {
	t1 := new(T1)
	fmt.Println(unsafe.Sizeof(int(0)))      // "8"
	fmt.Println(unsafe.Sizeof(float32(0)))  // "4"
	fmt.Println(unsafe.Alignof(float32(0))) // "4"
	fmt.Println(unsafe.Offsetof(t1.n))      // "8"
}
