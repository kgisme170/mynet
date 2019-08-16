package main
import (
	"fmt"
	"bytes"
	//log "github.com/sirupsen/logrus"
)
func main(){
	fmt.Println("hw")
	var sa = "a好"
	buf := bytes.NewBufferString(sa)
	fmt.Println(buf)
	fmt.Println("rune:", len([]rune(sa)))
	first := "社区"
	fmt.Println([]rune(first))
	fmt.Println([]byte(first))
	ss := "golangcaff"
	fmt.Println(ss[:3])
	s := "截取中文"
	res := []rune(s)
	fmt.Println(string(res[:2]))
}