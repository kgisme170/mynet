#include<stdio.h>
struct class2 {
    class2() { printf("class2 object\n"); }
};
void myConstructor2() __attribute__((constructor));
void myConstructor2() {
    class2 f_obj2;
}