package main

import (
	"fmt"
	"os"
	"syscall"
	"unsafe"
)

func main() {
	const n = 8
	a := int(unsafe.Sizeof(0)) * n

	mapFile, _ := os.Create("my.dat")
	_, _ = mapFile.Seek(int64(a-1), 0)
	mapFile.Write([]byte(" "))

	mmap, _ := syscall.Mmap(
		int(mapFile.Fd()),
		0,
		int(a),
		syscall.PROT_READ|syscall.PROT_WRITE,
		syscall.MAP_SHARED,
	)
	if err := mapFile.Close(); err != nil {
		fmt.Println(err)
		return
	}
	array := (*[n]int)(unsafe.Pointer(&mmap)) // from []byte to pointer and then cast
	syscall.Munmap(mmap)
	for i := 0; i < n; i++ {
		array[i] = i * i
	}
	for idx, v := range *array {
		fmt.Printf("idx=%d, v=%d\n", idx, v)
	}
}
