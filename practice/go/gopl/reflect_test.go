package main

import (
	"errors"
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

type T3 struct{}

func (t *T3) Do() {
	fmt.Println("hello")
}

func test1() {
	name := "Do"
	t := &T3{}
	reflect.ValueOf(t).MethodByName(name).Call(nil)
}

func (t *T3) Do2(a int, b string) {
	fmt.Println("hello"+b, a)
}

func test2() {
	name := "Do2"
	t := &T3{}
	a := reflect.ValueOf(1111)
	b := reflect.ValueOf("world")
	in := []reflect.Value{a, b}
	reflect.ValueOf(t).MethodByName(name).Call(in)
}

func (t *T3) Do3() (string, error) {
	return "hello", errors.New("new error")
}

func test3() {
	name := "Do3"
	t := &T3{}
	ret := reflect.ValueOf(t).MethodByName(name).Call(nil)
	fmt.Printf("strValue: %[1]v\nerrValue: %[2]v\nstrType: %[1]T\nerrType: %[2]T", ret[0], ret[1].Interface().(error))
}

type T4 struct {
	A int    `json:"aaa" test:"testaaa"`
	B string `json:"bbb" test:"testbbb"`
}

func test4() {
	t := T4{
		A: 123,
		B: "hello",
	}
	tt := reflect.TypeOf(t)
	for i := 0; i < tt.NumField(); i++ {
		field := tt.Field(i)
		if json, ok := field.Tag.Lookup("json"); ok {
			fmt.Println(json)
		}
		test := field.Tag.Get("test")
		fmt.Println(test)
	}
}

type newT struct {
	AA int
	BB string
}

func test5() {
	t := T4{
		A: 123,
		B: "hello",
	}
	tt := reflect.TypeOf(t)
	tv := reflect.ValueOf(t)

	newT := &newT{}
	newTValue := reflect.ValueOf(newT)

	for i := 0; i < tt.NumField(); i++ {
		field := tt.Field(i)
		newTTag := field.Tag.Get("newT")
		tValue := tv.Field(i)
		// newTValue.Elem().FieldByName(newTTag).Set(tValue) // panic
		newTfield := newTValue.Elem().FieldByName(newTTag)
		if newTfield.CanSet() {
			newTfield.Set(tValue)
		} else {
			fmt.Println("cannot set")
		}
	}

	fmt.Println(newT)
}

func test6() {
	a := 1
	t := reflect.TypeOf(a)
	switch t.Kind() {
	case reflect.Int:
		fmt.Println("int")
	case reflect.String:
		fmt.Println("string")
	}
}

type IT interface {
	mtest()
}

type T7 struct {
	A string
}

func (t *T7) mtest() {}

func test7() {
	t := &T7{}
	ITF := reflect.TypeOf((*IT)(nil)).Elem()
	tv := reflect.TypeOf(t)
	fmt.Println(tv.Implements(ITF))
}

func test8() {
	v := reflect.ValueOf(1)
	fmt.Println(v.Interface().(int))
}

func test9() {
	i := 1
	v := reflect.ValueOf(&i)
	v.Elem().SetInt(10)
	fmt.Println(i)
}

func Add(a, b int) int { return a + b }
func test10() {
	v := reflect.ValueOf(Add)
	if v.Kind() != reflect.Func {
		return
	}
	t := v.Type()
	argv := make([]reflect.Value, t.NumIn())
	for i := range argv {
		if t.In(i).Kind() != reflect.Int {
			return
		}
		argv[i] = reflect.ValueOf(i)
	}
	result := v.Call(argv)
	if len(result) != 1 || result[0].Kind() != reflect.Int {
		return
	}
	fmt.Println(result[0].Int()) // #=> 1
}

func test0() {
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

func TestReflect(t *testing.T) {
	/*
		test0()
		test1()
		test2()
		test3()
		test4()*/
	test5()
	/*
		test6()
		test7()
		test8()
		test9()
		test10()*/
}
