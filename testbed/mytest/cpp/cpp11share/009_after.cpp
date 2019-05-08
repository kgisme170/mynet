#include<iostream>
using namespace std;
static int i=0;
struct A {
    A() {}

    A(const A &) { i += 1; }

    A(A &&) { i += 2; }
};
int main() {
    A obj;
    A obj2(move(obj));
    cout << i << endl; // 2
    return 0;
}