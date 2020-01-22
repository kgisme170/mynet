package main

import (
	"log"
	"os"
)

func main() {
	logFile, err := os.Create("uselog.txt")
	defer logFile.Close()
	if err != nil {
		log.Fatalln(err)
	}
	debugLog := log.New(logFile, "[Debug]", log.LstdFlags)
	debugLog.Println("A debug message here")
	debugLog.SetPrefix("[Info]")
	debugLog.Println("A Info Message here ")
	debugLog.SetFlags(debugLog.Flags() | log.Llongfile | log.Lmicroseconds)
	debugLog.Println("A different prefix")
}
