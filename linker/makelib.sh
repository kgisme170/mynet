#!/bin/bash
g++ -c b.cpp -o b.o
ar cr libb.a b.o
g++ -c d.cpp -o d.o
ar cr libd.a d.o
g++ a.cpp -L. -lb -ld

g++ -c doubledefine01.cpp -fPIC
g++ -c doubledefine02.cpp -fPIC

#g++ doubledefinemain.cpp doubledefine01.cpp doubledefine02.cpp -o doubledefineCpp
#g++ doubledefinemain.cpp doubledefine01.o doubledefine02.o -o doubledefineObj

#ar cr libdoubledefineArchive.a doubledefine01.o doubledefine02.o
#g++ doubledefinemain.cpp -L. -ldoubledefineArchive

g++ -o libdoubledefine01.so --shared -rdynamic doubledefine01.o
g++ -o libdoubledefine02.so --shared -rdynamic doubledefine02.o
#g++ -o libdoubledefineboth.so --shared -rdynamic doubledefine01.o doubledefine02.o
#g++ doubledefinemain.cpp -L. -ldoubledefineboth -o doubledefineBoth
g++ doubledefinemain.cpp -L. -ldoubledefine01 -ldoubledefine02 -o doubledefineSo
