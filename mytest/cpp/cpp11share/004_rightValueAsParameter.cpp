#include<iostream>
using namespace std;
struct A{};
void f(A&& a)//f接受右值引用做形参
{
    cout << "函数内部的a仍然是左值!"
         << &a << endl;
}
int main()
{
    A obj;
    f(A()); //f接受右值作为实参
    return 0;
}