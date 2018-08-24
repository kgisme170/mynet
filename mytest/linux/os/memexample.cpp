#include<stdio.h>
#include<malloc.h>
#include<unistd.h>
int bssvar;
int data_var0=1;
int main(){
    printf("下面是进程内存的类型\n");
    printf("Text location:%p\n", main);
    int stack_var0=2;
    printf("First variable on stack: %p\n",&stack_var0);
    int stack_var1=3;
    printf("Second variable on stack:%p\n",&stack_var1);
    printf("Data location 1:%p\n",&data_var0);
    static int data_var1=4;
    printf("Data location 2:%p\n",&data_var1);
    static int data_var2=5;
    printf("Data location 3:%p\n",&data_var2);

    printf("Bss location:%p\n", &bssvar);
    char* b=(char*)malloc(1);
    printf("Heap location 1:%p\n", b);

    b=(char*)malloc(1);
    printf("Heap location 2:%p\n", b);
    getchar();
    return 0;
}
