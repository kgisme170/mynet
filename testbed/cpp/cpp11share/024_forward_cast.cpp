#include<stdio.h>
#include<utility>
struct M {
    M() {}

    M(M &&) { printf("移动\n"); }

    M(const M &) { printf("拷贝\n"); }
};
struct N:M {
    N() {}

    N(N &&) { printf("移动\n"); }

    N(const N &) { printf("拷贝\n"); }
};
void g(M&&){printf("调用右值引用g\n");}
template<class T>
void f(T&& obj){g(std::forward<M>(obj));}
int main() {
    f(N());//调用右值引用g
    return 0;
}