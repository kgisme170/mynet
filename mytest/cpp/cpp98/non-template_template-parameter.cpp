//非类型模板参数：必须是常量表达式，非类型模板参数是整型模板参数，必须是编译期常量，局部变量不行。
#include<stdio.h>
#include<stdio.h>
int array[2];
template<int* array>
void f(){printf("array\n");}
int main(){
 f<array>();
 return 0;
}