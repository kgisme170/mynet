#include<stdio.h>
template<class T>void f(T){printf("T\n");}
template<>       void f(int*){printf("int*\n");}
template<class T>void f(T*){printf("T*\n");}
int main(int argc,char**){
    int* p=&argc;
    f(p);
    return 0;
}