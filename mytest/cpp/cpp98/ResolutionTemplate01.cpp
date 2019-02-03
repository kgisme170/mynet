#include<stdio.h>
template<class T>void f(T){printf("T\n");}
template<class T>void f(T*){printf("T*\n");}
template<>       void f(int*){printf("int*\n");}
int main(int argc,char**){
    int* p=&argc;
    f(p); // int*
    return 0;
}