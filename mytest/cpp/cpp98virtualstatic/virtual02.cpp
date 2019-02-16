#include<stdio.h>
struct B {
    virtual void pure() = 0;

    void h() {
        printf("Base\n");
        pure();
    }

    ~B() { h(); }
};
struct D : B {
    void pure() {}
};
int main() {
    D d;
    return 0;
}