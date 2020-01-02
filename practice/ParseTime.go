package main

import (
	"bufio"
	"encoding/json"
	"fmt"
	"io"
	"io/ioutil"
	"os"
	"time"
)

type LatencyNode struct {
	BackendID        string    `json:"id"`
	Delta            time.Time `json:"delta"`
	ActualArrival    time.Time `json:"arrivialTime"`
	LatencyInMinutes int32     `json:"latencyMins"`
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
		fmt.Println("-----")
	}
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

	// traverse the whole json tree, bfs
	// current := &latencyNode

	arrivalTimeBegin := latencyNode.ActualArrival

	fmt.Printf("%v\n", latencyNode.BackendID)
	fmt.Printf("%v\n", latencyNode.Delta)
	fmt.Printf("%v\n", arrivalTimeBegin)
	fmt.Printf("%v\n", latencyNode.LatencyInMinutes)
	//
}
