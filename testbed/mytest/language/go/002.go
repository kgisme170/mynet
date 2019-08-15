package main

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

func test1() {
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

func main() {
}
