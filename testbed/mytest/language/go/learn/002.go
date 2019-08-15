package mygo

import (
	"errors"
	"fmt"
	"math"
)

func Factorial(n uint64) (result uint64) {
	if n > 0 {
		result = n * Factorial(n-1)
		return result
	}
	return 1
}

type Phone interface {
	call()
}

type NokiaPhone struct {
}

func (nokiaPhone NokiaPhone) call() {
	fmt.Println("I am Nokia, I can call you!")
}

type IPhone struct {
}

func (iPhone IPhone) call() {
	fmt.Println("I am iPhone, I can call you!")
}

func Sqrt(f float64) (float64, error) {
	if f < 0 {
		return 0, errors.New("math: square root of negative number")
	}
	return math.Sqrt(f), errors.New("OK")
}

func test003() {
	fmt.Println("")
	var countryCapitalMap map[string]string = make(map[string]string)
	countryCapitalMap["france"] = "paris"
	for country := range countryCapitalMap {
		fmt.Println(country, "capital", countryCapitalMap[country])
	}
	capital, ok := countryCapitalMap["us"]
	if ok {
		fmt.Println("capital:" + capital)
	} else {
		fmt.Println("us capital not found")
	}
	var i = 5
	fmt.Printf("%d factorial is %d\n", i, Factorial(uint64(i)))
	var sum = 17
	var count = 5
	var mean float32

	mean = float32(sum) / float32(count)
	fmt.Printf("mean =: %f\n", mean)

	var phone Phone

	phone = new(NokiaPhone)
	phone.call()

	phone = new(IPhone)
	phone.call()

	_, err := Sqrt(-1)

	if err != nil {
		fmt.Println(err)
	}
}

type My struct {
	x int
	y int
}

func (m My) Print() {
	fmt.Println(m)
}

func (m My) f1() {
	m.x++
}

func (m *My) f2() {
	m.x++
}

type bigint int64
type smallint int8

var y smallint = 1

type Books struct {
	title   string
	author  string
	subject string
	book_id int
}

func test2() {
	fmt.Println("OK")
	My{3, 2}.Print()
	var Book1 Books
	var Book2 Books

	Book1.title = "Go language"
	Book1.author = "www.runoob.com"
	Book1.subject = "Go language course"
	Book1.book_id = 6495407

	Book2.title = "Python course"
	Book2.author = "www.runoob.com"
	Book2.subject = "Python language course"
	Book2.book_id = 6495700

	fmt.Printf("Book 1 title : %s\n", Book1.title)
	fmt.Printf("Book 1 author : %s\n", Book1.author)
	fmt.Printf("Book 1 subject : %s\n", Book1.subject)
	fmt.Printf("Book 1 book_id : %d\n", Book1.book_id)

	fmt.Printf("Book 2 title : %s\n", Book2.title)
	fmt.Printf("Book 2 author : %s\n", Book2.author)
	fmt.Printf("Book 2 subject : %s\n", Book2.subject)
	fmt.Printf("Book 2 book_id : %d\n", Book2.book_id)
}
func main002() {
	var m My = My{2, 3}
	m.f1()
	fmt.Println(m)
	m.f2()
	fmt.Println(m)

	m2 := new(My)
	m2.f1()

	m2.f2()
	fmt.Println(m2)

	var m3 *My = new(My)
	m3.f2()
	fmt.Println(m3)
}
