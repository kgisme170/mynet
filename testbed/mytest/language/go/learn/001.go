package mygo

import (
	"fmt"
	"math"
	"math/cmplx"
	"runtime"
	"strings"
	"time"
)

func add(x int, y int) int {
	return x + y
}

func minus(x, y int) int {
	return x - y
}

func swap(x, y string) (string, string) {
	return y, x
}

func split(sum int) (x, y int) {
	x = sum * 4 / 9
	y = sum - x
	return
}

var (
	ToBe   bool       = false
	MaxInt uint64     = 1<<64 - 1
	z      complex128 = cmplx.Sqrt(-5 + 12i)
)

const (
	big   = 1 << 100
	Small = big >> 99
)

func sqrt(x float64) string {
	if x < 0 {
		return sqrt(-x) + "i"
	}
	return fmt.Sprint(math.Sqrt(x))
}

func pow(x, n, lim float64) float64 {
	if v := math.Pow(x, n); v < lim {
		return v
	}
	return lim
}

func funct(x int) (y int) {
	y = x * x
	return
}

func useSwitch(x int) {
	switch y := x + 2; y {
	case 3:
		fmt.Println("3")
	case 5:
		fmt.Println("5")
	case funct(x):
		fmt.Println("funct(x)")
	case 4:
		fmt.Println("4")
	}
}

func test001() {
	var c, python, java = true, false, "no"
	fmt.Println(add(43, 42), minus(43, 42))
	fmt.Println(math.Pi)
	fmt.Printf("Hello, world. %g", math.Sqrt(7))
	fmt.Println()
	fmt.Println(swap("hello", "world"))
	fmt.Println(split(17))
	fmt.Println(c, python, java)

	var ii, j int = 10, 23
	k := 3
	l := 5
	fmt.Println(ii, j, k, l)

	fmt.Println(ToBe, MaxInt, z)
	const f = "%T(%v)\n"
	fmt.Printf(f, z, z)

	var i int
	var ff float64
	var b bool
	var s string
	fmt.Printf("%v %v %v %q\n", i, ff, b, s)

	var u uint = uint(ff)
	ii = 42
	fff := float64(i)
	fmt.Println(ii, fff, u)

	var e int
	g := e
	fmt.Println(e, g)
	const World = "world"
	const True = true
	fmt.Println(True, World, Small)

	sum := 0
	for i := 0; i < 10; i++ {
		sum += i
	}
	fmt.Println(sum)

	sum = 1
	for sum < 1000 {
		sum += sum
	}
	fmt.Println(sum)

	fmt.Println(sqrt(2), sqrt(4))
	fmt.Println(
		pow(3, 2, 10),
		pow(3, 3, 20),
	)
	fmt.Print("Go runs on ")
	switch os := runtime.GOOS; os {
	case "darwin":
		fmt.Println("OS X.")
	case "linux":
		fmt.Println("Linux.")
	default:
		// freebsd, openbsd,
		// plan9, windows...
		fmt.Printf("%s.", os)
	}

	switch today := time.Now().Weekday(); time.Saturday {
	case today:
		fmt.Println("Today")
	case today + 1:
		fallthrough
		//fmt.Println("Tomorrow")
	case today + 2:
		fmt.Println("In two days")
	default:
		fmt.Println("Other")
	}

	fmt.Println(funct(2))
	useSwitch(2)
}

/* Print something */
func test002() {
	defer fmt.Println("at exit")
	fmt.Println("begin")
	for i := 0; i < 10; i++ {
		defer fmt.Println(i)
	}
	fmt.Println("done")
	i := 42
	p := &i
	fmt.Println(p, i, *p)
	fmt.Println(Vertex{1, 2})
	v := Vertex{2, 3}
	v.X = 5
	pv := &v
	pv.X = 1e9
	fmt.Println(v)
	v2 := Vertex{X: 1}
	v3 := Vertex{}
	p2 := &Vertex{3, 4}
	fmt.Println(v2, v3, p2)

	var aa [10]int
	fmt.Println(aa)

	a := make([]int, 5)
	printSlice("a", a)
	b := make([]int, 0, 5)
	printSlice("b", b)
	c := b[:2]
	printSlice("c", c)
	d := c[2:5]
	printSlice("d", d)
	var z []int
	fmt.Println(z, len(z), cap(z))
	if z == nil {
		fmt.Println("nil!")
	}
}

type Vertex struct {
	X int
	Y int
}

func printBoard(s [][]string) {
	for i := 0; i < len(s); i++ {
		fmt.Printf("%s\n", strings.Join(s[i], " "))
	}
}

func printSlice(s string, x []int) {
	fmt.Printf("%s len=%d cap=%d %v\n",
		s, len(x), cap(x), x)
}

func main001() {
	game := [][]string{
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
		[]string{"_", "_", "_"},
	}

	game[0][0] = "X"
	game[0][2] = "X"
	game[1][0] = "O"
	game[2][0] = "X"
	game[2][2] = "O"

	printBoard(game)
	s := []int{1, 2, 3}
	for i := 0; i < len(s); i++ {
		fmt.Printf("s[%d]=%d\n", i, s[i])
	}

	var a []int
	printSlice("a", a)

	a = append(a, 0)
	printSlice("a", a)

	a = append(a, 1)
	printSlice("a", a)

	a = append(a, 2, 3, 4)
	printSlice("a", a)

	var pow = []int{1, 2, 4, 8, 16, 32, 64, 128}
	for i, v := range pow {
		fmt.Printf("2**%d = %d\n", i, v)
	}
	pow = make([]int, 10)
	for i := range pow {
		pow[i] = 1 << uint(i)
	}
	for _, value := range pow {
		fmt.Printf("%d\n", value)
	}
}
