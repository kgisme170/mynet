#include <iostream>
using namespace std;
template <class T>
struct Base {
    void f() {
        // ...
        static_cast<T*>(this)->cmp();
        // ...
    }
};

struct Derived : Base<Derived> {
    void cmp(){cout<<"cmp function\n";}
};

int main(){
    Derived d;
    d.f();
    return 0;
}