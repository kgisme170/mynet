package main

import (
	"fmt"
	"reflect"
	"strings"
	"testing"
)

var v1 interface{} = 1
var v2 interface{} = "string"
var v3 interface{} = &v2

//var v4 interface{}=strcut{x int}(x:1)
//var v5 interface{}=&strcut{x int}(x:1)

func reflectValue() {
	v := reflect.ValueOf(3) // a reflect.Value
	fmt.Println(v)          // "3"
	fmt.Printf("%v\n", v)   // "3"
	fmt.Println(v.String()) // NOTE: "<int Value>"
}

func TestReflect(t *testing.T) {
	fmt.Printf("%T\n", 3)
	v := reflect.TypeOf(3)
	fmt.Println(v)
	fmt.Println(v.String())
	fmt.Println(v.Kind())

	var a interface{} = 100
	var b interface{} = "hi"
	fmt.Println(a == b)
	var v1 interface{}
	v1 = 6.78

	if v, ok := v1.(float64); ok {
		fmt.Println(v, ok)
	} else {
		fmt.Println(v, ok)
	}
	reflectValue()

	got := strings.Split("a:b:c", ":")
	want := []string{"a", "b", "c"}
	fmt.Println(reflect.DeepEqual(got, want))
}
