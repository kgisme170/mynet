#!/bin/bash
gcc -o libd2.so d2.c -fPIC --shared
gcc -o libd1.so d1.c -fPIC --shared
gcc m.c -L. -ld1 -o md1
gcc m.c -L. -ld2 -o md2

g++ -o libd1++.so d1.cpp -fPIC --shared
g++ -o libd2++.so d2.cpp -fPIC --shared
g++ m.cpp -L. -ld1++ -o md1++
g++ m.cpp -L. -ld2++ -o md2++
