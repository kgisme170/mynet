#include<stdio.h>
struct class1{
    class1(){printf("class1类型\n");}
};
void myConstructor() __attribute__((constructor));
void myConstructor()
{
    class1 f_obj;
}