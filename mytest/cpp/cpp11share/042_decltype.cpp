#include<stdio.h>
int main() {
    int i = 2;
    decltype(i + 1) j = i;//int
    ++j;
    printf("i=%d\n", i);//还是2
    decltype(i) k = i;//int
    ++k;
    printf("i=%d\n", i);//还是2
    decltype((i)) l = i;//i的引用
    ++l;
    printf("i=%d\n", i);//变成3
    return 0;
}