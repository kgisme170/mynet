package main

import (
	"fmt"
	"testing"
)

type T struct {
	m int
}

func (t *T) print() {
	fmt.Printf("t.m=%d\n", t.m)
}

func (t1 *T) compare(t2 *T) {
	t1.print()
	t2.print()
}

func useArray(a [2]int) {
	a[0] = 2
	a[1] = 3
}

func useSlice(a []int) {
	a[0] = 2
	a[1] = 3
}

type T1 struct {
	m int
	n int
}

type T2 struct {
	T1
	j int
}

func newT1() *T1 {
	return &T1{}
}

func TestPointer(t *testing.T) {
	t1 := new(T)
	t2 := &T{} // what's the core difference between them?
	t1.print()
	t2.print()
	f1 := t1.print
	f1()
	a := [2]int{1, 2}
	useArray(a)
	fmt.Println(a[0])
	fmt.Println(a[1])
	b := []int{1, 2}
	useSlice(b)
	fmt.Println(b[0])
	fmt.Println(b[1])

	t3 := new(T2)
	t3.m = 3
	t4 := newT1()
	fmt.Println(t4)
	pf := t1.print
	pf()
	pf2 := (*T).print
	pf2(t1)
	pf3 := (*T).compare
	pf3(t1, t2)

	new(T).print()
}
