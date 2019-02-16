#include<iostream>
using namespace std;
struct A {
    A() { cout << "A ctor\n"; }

    A(const A &) { cout << "A copy ctor\n"; }

    A &operator=(const A &) {
        cout << "A operator=\n";
        return *this;
    }

    A(A &&) { cout << "A move ctor\n"; }

    A &operator=(A &&) {
        cout << "A move operator=\n";
        return *this;
    }
};
struct B {
    A a;
    int i;
};
int main() {
    cout << "direct initialization\n";
    B b1{A(), 1};//require A(&&)
    cout << "=============\n";
    B b2 = {A(), 2};//require A(&&)
    cout << "copy initialization\n";
    A a;
    B b3 = {a, 3};//require A(const A&)
    return 0;
}