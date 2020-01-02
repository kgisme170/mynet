#include<stdio.h>
void f(short){printf("short\n");}
int main() {
    int i = 0;
    f(i); // short
    return 0;
}
void f(int) {
    printf("int\n");
}