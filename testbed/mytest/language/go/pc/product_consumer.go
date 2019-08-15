package main
import (
	"fmt"
	"time"
)
func Produce(ch chan<- int) {
	for i := 0; i < 100; i++ {
		fmt.Println("Product:", i)
		ch <- i
	}
}
func Consumer(ch <-chan int) {
	for i := 0; i < 100; i++ {
		a := <-ch
		fmt.Println("Consmuer:", a)
	}
}
func main() {
	ch := make(chan int, 10)
	go Produce(ch)
	go Consumer(ch)
	time.Sleep(500 * time.Millisecond)
}