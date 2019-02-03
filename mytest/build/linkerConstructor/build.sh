#!/bin/sh
g++ --shared -o libmyshare1.so class1.o
g++ --shared -o libmyshare2.so class2.o
g++ main.cpp -L. -lmyshare1 -lmyshare2