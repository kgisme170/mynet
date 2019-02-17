#include<stdio.h>
struct class1 {
    class1() { printf("class1 object\n"); }
};
void myConstructor() __attribute__((constructor));
void myConstructor() {
    class1 f_obj;
}