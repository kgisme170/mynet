#include<stdio.h>
#include<limits.h>
int main(){
    unsigned int input = 3;
    int output = 6;
    int eax;
    __asm__ __volatile__("movl %0, %%eax;\n\t;" \
                         "movl %%eax, %1\n\t;"
                         :"=r"(eax)
                         :"r"(input));
    __asm__ __volatile__("addl %2, %0"
                         :"=r"(output)
                         :"0"(output),"g"(input));
    printf("%d, %d, %d\n", input, output, eax);
/*
    int flag;
    __asm__ ("pushfl;"
             "popl %0"
             :"=g"(flag));
    printf("标志寄存器的值=0x%x\n",flag);//‭001000000110‬
    int i=INT_MAX, j=INT_MAX;
    int k=i+j;//CF=1
    __asm__ ("pushfl;"
             "popl %0"
             :"=g"(flag));
    printf("标志寄存器的值=0x%x\n",flag);//‭101010010010‬
    k=i-j;//ZF=1
    __asm__ ("pushfl;"
             "popl %0"
             :"=g"(flag));
    printf("标志寄存器的值=0x%x\n",flag);//‭001001000110‬
*/
    return 0;
}
