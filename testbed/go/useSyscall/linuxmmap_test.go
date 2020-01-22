package main

import (
	"fmt"
	"os"
	"syscall"
	"testing"
	"unsafe"
)

func TestMmap(t *testing.T) {
	n := 32
	t := int(unsafe.Sizeof(0)) * n

	map_file, _ := os.Create("my.dat")
	_, _ = map_file.Seek(int64(t-1), 0)
	map_file.Write([]byte(" "))

	mmap, _ := syscall.Mmap(
		map_file.Fd(),
		0,
		int(t),
		syscall.PROT_READ|syscall.PROT_WRITE,
		syscall.MAP_SHARED,
	)
	defer syscall.Munmap(mmap, 0)
	map_array := (*[32]int)(unsafe.Pointer(&mmap))

	for i := 0; i < n; i++ {
		map_array[i] = i * i
	}
	fmt.Println(*map_array)
}
