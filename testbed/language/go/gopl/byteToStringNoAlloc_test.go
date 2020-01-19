package main

import (
	"fmt"
	"reflect"
	"testing"
	"unsafe"
)

func byteToString(b []byte) string {
	return string(b)
}

func byteToStringNoAlloc(b []byte) string {
	if len(b) == 0 {
		return ""
	}
	sh := reflect.StringHeader{uintptr(unsafe.Pointer(&b[0])), len(b)}
	return *(*string)(unsafe.Pointer(&sh))
}

func TestStringNoAlloc(t *testing.T) {
	b := []byte("hello")
	fmt.Printf("1st element of slice: %d\n", &b[0])

	str := byteToString(b)
	sh := (*reflect.StringHeader)(unsafe.Pointer(&str))
	fmt.Printf("New alloc: %v\n", sh)

	toStr := byteToStringNoAlloc(b)
	shNoAlloc := (*reflect.StringHeader)(unsafe.Pointer(&toStr))
	fmt.Printf("No alloc: %v\n", shNoAlloc) // same as &b[0]
}
