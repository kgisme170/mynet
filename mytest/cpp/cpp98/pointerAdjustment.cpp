#include<cstdio>
using namespace std;
struct base01 {
    virtual void f() { printf("base01\n"); }

    int i;
};
struct base02 {
    virtual void f() { printf("base02\n"); }

    int j;
};
struct derive:base01,base02 {
};
int main(void) {
    void (base01::*pf1)() =&base01::f;
    printf("%lu\n", sizeof(pf1));

    void (base02::*pf2)() =&base02::f;
    printf("%lu\n", sizeof(pf2));

    derive d;
    base02 *pb = &d;
    (pb->*pf2)();
    return 0;
}