package main

import (
	"errors"
	"testing"
)

func Division(a, b float64) (float64, error) {
	if b == 0 {
		return 0, errors.New("Divide by 0 error")
	}

	return a / b, nil
}

func Benchmark_Division(b *testing.B) {
	for i := 0; i < b.N; i++ {
		Division(4, 5)
	}
}

// go test -bench=.
// go test -bench=".*"

func Benchmark_TimeConsumingFunction(b *testing.B) {
	b.StopTimer()
	b.StartTimer()
	for i := 0; i < b.N; i++ {
		Division(4, 5)
	}
}
