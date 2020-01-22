package main

import (
	"fmt"
	"syscall"
	"testing"
	"unsafe"
)

const (
	yesnocancel = 0x00000003
)

var (
	user32, _      = syscall.LoadLibrary("user32.dll")
	messageBoxw, _ = syscall.GetProcAddress(user32, "MessageBoxW")
)

func intPtr(n int) uintptr {
	return uintptr(n)
}

func strPtr(s string) uintptr {
	return uintptr(unsafe.Pointer(syscall.StringToUTF16Ptr(s)))
}

func messageBox(caption, text string, style uintptr) (result int) {
	ret, _, err := syscall.Syscall9(messageBoxw,
		4, 0,
		strPtr(text),
		strPtr(caption),
		style,
		0, 0, 0, 0, 0,
	)
	if err != 0 {
		fmt.Printf("Call MessageBox: %v\n", err)
	}
	return int(ret)
}

func messagebox2(title, text string) {
	user32 := syscall.NewLazyDLL("user32.dll")
	MessageBoxW := user32.NewProc("MessageBoxW")
	MessageBoxW.Call(intPtr(0), strPtr(text), strPtr(title), intPtr(0))
}

func TestMessageBox(t *testing.T) {
	defer syscall.FreeLibrary(user32)

	ret := messageBox("Caption", "text.", yesnocancel)
	fmt.Printf("return=%d\n", ret)
	messagebox2("title", "text")
}
