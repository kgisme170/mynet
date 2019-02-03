#include<stdio.h>
int main(){
    const int i=10;
    int *pi = (int *)(&i); //强制类型转换
    *pi = 20;
    printf("i = %d *pi = %d\n",i,*pi); // i = 10 *pi = 20
    const int j=i;
    printf("j = %d\n", j); // j = 10
    return 0;
}