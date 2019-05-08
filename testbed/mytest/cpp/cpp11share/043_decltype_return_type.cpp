#include<iostream>
using namespace std;
struct A {
    A() : m_s("hello") { cout << "构造" << endl; }

    ~A() { cout << "析构" << endl; }

    string m_s;

    string &&get() { return move(m_s); }
};
A factory(){return A();}
auto get()->decltype(factory().get()) {
    cout << "-->get函数" << endl;
    return factory().get();
}
void f(string&&){cout<<"-->f函数"<<endl;}
int main() {
    f(get());
    return 0;
}