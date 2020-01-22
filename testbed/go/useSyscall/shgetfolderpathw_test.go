package main

import (
	"fmt"
	"syscall"
	"testing"
	"unsafe"
)

var (
	shell         = syscall.MustLoadDLL("Shell32.dll")
	getFolderPath = shell.MustFindProc("SHGetFolderPathW")
)

const (
	desktop = 0
	appdata = 26
)

func TestShGetFolderPathW(t *testing.T) {
	b := make([]uint16, syscall.MAX_PATH)
	// https://msdn.microsoft.com/en-us/library/windows/desktop/bb762181%28v=vs.85%29.aspx
	r, _, err := getFolderPath.Call(0, desktop, 0, 0, uintptr(unsafe.Pointer(&b[0])))
	if uint32(r) != 0 {
		fmt.Println(err)
		return
	}
	desktopDir := syscall.UTF16ToString(b)

	r, _, err = getFolderPath.Call(0, appdata, 0, 0, uintptr(unsafe.Pointer(&b[0])))
	if uint32(r) != 0 {
		fmt.Println(err)
		return
	}
	appdataDir := syscall.UTF16ToString(b)

	fmt.Printf("directory ID：%d  directory location：%s\n", desktop, desktopDir)
	fmt.Printf("directory ID：%d  directory location：%s\n", appdata, appdataDir)
}
