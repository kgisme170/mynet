#include<stdio.h>
#include<utility>
struct M {
    M() {}

    M(M &&) { printf("移动\n"); }

    M(const M &) { printf("拷贝\n"); }
};
void f(const M&){printf("左值引用版本\n");}
void f(M&&     ){printf("右值引用版本\n");}
int main() {
    const M obj;
    f(std::move(obj)); // 左值引用版本
    return 0;
}