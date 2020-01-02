package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"strings"
	"time"
)

type Node2 struct {
	Data      int      `json:"data"`
	ListNodes []*Node2 `json:"listNodes""`
}

type LatencyNode struct {
	BackendID        string         `json:"id"`
	Delta            time.Time      `json:"delta"`
	ActualArrival    time.Time      `json:"arrivialTime"`
	LatencyInMinutes int32          `json:"latencyMins"`
	Upstream         []*LatencyNode `json:"upstream"`
	// DeltaOffsetInMinutes int32          `json:"deltaOffsetInMins"`
}
type Message struct {
	Name string
	Body string
	Time int64
}

func useBufio(filename string) {
	latencyNode := LatencyNode{}
	fi, err := os.Open(filename)
	if err != nil {
		fmt.Printf("Error: %s\n", err)
		return
	}
	defer fi.Close()

	br := bufio.NewReader(fi)
	for {
		a, _, c := br.ReadLine()
		if c == io.EOF {
			break
		}
		fmt.Println(string(a))
		_ = json.Unmarshal(a, &latencyNode)
		fmt.Println(latencyNode.BackendID)
		for _, element := range latencyNode.Upstream {
			fmt.Println(element.BackendID)
		}
		fmt.Println("-----")
	}
}

func shortDur(d time.Duration) string {
    s := d.String()
    if strings.HasSuffix(s, "m0s") {
        s = s[:len(s)-2]
    }
    if strings.HasSuffix(s, "h0m") {
        s = s[:len(s)-2]
    }
    return s
}

func main() {
	filename := os.Args[1]
	fmt.Println("filename = " + filename)

	latencyNode := LatencyNode{}

	r, err := ioutil.ReadFile(filename)
	if err != nil {
		panic(err)
	}
	_ = json.Unmarshal([]byte(r), &latencyNode)

	// 先构造一棵树
	outputFile := "latencyGraphViz.txt"
	of, e3 := os.Create(outputFile)
	if e3 != nil {
		fmt.Println("Create outputFile : " + outputFile + " failed")
		os.Exit(-2)
	}
	of.WriteString("digraph G {\n")

	// traverse the whole json tree, bfs
	nodes := make([]*LatencyNode, 0, 128)
	nodes = append(nodes, &latencyNode)
	fmt.Println(len(nodes))

	m2 := make(map[string]string)

	for len(nodes) != 0 {
		current := nodes[0]
		arrivalTimeEnd := current.ActualArrival
		upstreams := current.Upstream
		nUpstreams := len(upstreams)
		fmt.Printf("BackendID: %s, len(upstream)=%d\n", current.BackendID, nUpstreams)
		nodes = nodes[1:] // 后续的upstream节点拿出来

		// 如果上游的个数超过10个,那么在绘图的时候, 只取出时间最早的那个, 并显示10+这样的信息
		if nUpstreams < 10 {
			for _, upstream := range upstreams {
				//每个节点都要计算和current之间的时间间隔
				sub := arrivalTimeEnd.Sub(upstream.ActualArrival) // milliseconds
				fmt.Printf("End=%v, Begin=%v, sub=%d(s)\n", arrivalTimeEnd, upstream.ActualArrival, sub / 1000000000)
				if sub > 0 {
					k := upstream.BackendID + current.BackendID
					_, ok := m2[k]
					if !ok {
						line := "\"" + upstream.BackendID + "\"" + " -> " + "\"" + current.BackendID + "\""
						line += "[ label = \"" + shortDur(sub) + "\" ]"
						line += "\n"
						fmt.Print(line)
						of.WriteString(line)
						m2[k] = "ok"
					}
				}
				//追加
				nodes = append(nodes, upstream)
				fmt.Printf("len(nodes)=%d\n", len(nodes))
			}
		} else {
			t := upstreams[0]
			for _, upstream := range upstreams[1:] {
				if t.ActualArrival.After(upstream.ActualArrival) {
					t = upstream // 找到更早的一个事件
				}
			}
			sub := arrivalTimeEnd.Sub(t.ActualArrival) // milliseconds
			fmt.Printf("End=%v, Begin=%v, sub=%d(s)\n", arrivalTimeEnd, t.ActualArrival, sub / 1000000000)
			if sub > 0 {
				k := t.BackendID + current.BackendID
				_, ok := m2[k]
					if !ok {
					line := "\"10+" + t.BackendID + "\"" + " -> " + "\"" + current.BackendID + "\""
					line += "[ label = \"" + shortDur(sub) + "\" ]"
					line += "\n"
					fmt.Print(line)
					of.WriteString(line)
					m2[k] = "ok"
				}
			}
		}
		fmt.Println()
	}

	of.WriteString("}\n")
	e4 := of.Close()
	if e4 != nil {
		fmt.Println(e4)
		return
	}
}
