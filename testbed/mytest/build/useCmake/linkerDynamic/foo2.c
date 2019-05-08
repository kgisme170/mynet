#include<stdio.h>
void f(void);
int x = 15;
int y = 16;
int main() {
    f();
    printf("%d,%d\n", x, y);
    return 0;
}