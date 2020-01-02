#include<utility>
#include<stdio.h>
class M {
    M(const M &) {}

public:
    M() { printf("构造\n"); }

    M(M &&) { printf("移动\n"); }
};
void f(M obj){}
int main() {
    f(M());//构造
    printf("--------\n");
    M obj;//构造
    f(std::move(obj));//移动
    return 0;
}