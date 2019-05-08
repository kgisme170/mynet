#include <iostream>
using namespace std;
struct S {
    virtual void f() = 0;

    virtual ~S() {}
};
void S::f() { cout<<"Impl pure function\n"; }
struct Impl : S {
    void f() { S::f(); }
};

int main() {
    S *s = new Impl();
    s->f();
    delete s;
    return 0;
}