#include<stdio.h>
void f(int i) {
    short s = i;
    f(s);
}
void f(short){printf("short\n");}
int main() {
    int i = 0;
    f(i); // recursive and abort()
    return 0;
}