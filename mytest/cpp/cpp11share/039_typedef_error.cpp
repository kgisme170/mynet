#include<iostream>
using namespace std;
struct A {
    A() {}
    A(A&&a) { cout << "move ctor" << endl; }
    A(const A&) { cout << "copy ctor" << endl; }
};
template<class T>
struct Def {
    typedef T& ref;
};
void f(Def<A>::ref&& r) {}
int main()
{
    A a;
    f(a);
    f(move(a));//错误，f函数接受的是A&
    return 0;
}