#include<stdio.h>
struct M {
    int *pi;

    M() : pi(new int(12)) { printf("构造\n"); }

    M(const M &) { printf("拷贝\n"); }

    ~M() {
        delete pi;
        pi = NULL;
    }
};
struct B {
    M &ref;

    B(M &obj) : ref(obj) {}
};
int main() {
    M instance;
    B big(instance);
    printf("%d\n", *big.ref.pi);//打印12
    instance.~M();
    printf("%d\n", *big.ref.pi);//崩溃
    return 0;
}