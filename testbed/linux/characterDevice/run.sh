#!/bin/bash
make
insmod ./hello.ko
cat /proc/devices |grep hello
mknod /dev/hello c 111 0
gcc app.c -o app
