#include<stdio.h>
void f(void);
int x;
int main(){
    x = 15;
    f();
    printf("%d\n",x);
    return 0;
}