#include<stdio.h>
int b(int);
int c(int);
int a(int x) {
    int sum = b(x) + c(x);
    printf("%d\n", sum);
    return sum;
}
