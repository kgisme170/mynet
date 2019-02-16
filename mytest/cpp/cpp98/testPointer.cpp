#include<stdio.h>
struct S {
    int i, j, k;

    void f() {}
};
int main() {
    int S::*p = &S::i;
    void (S::*f)() = &S::f;
    printf("%lu,%lu\n", sizeof(p), sizeof(f));
    return 0;
}

