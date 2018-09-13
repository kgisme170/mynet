#include<stdio.h>
template<class T,size_t N>
void f(T (&a)[N]){printf("fd\n");}
int main(){
    int fd[2];
    f(fd);
    return 0;
}