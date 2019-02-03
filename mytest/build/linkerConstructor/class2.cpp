#include<stdio.h>
struct class2{
    class2(){printf("class2类型\n");}
};
void myConstructor() __attribute__((constructor));
void myConstructor()
{
    class2 f_obj;
}