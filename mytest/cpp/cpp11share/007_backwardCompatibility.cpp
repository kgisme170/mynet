#include<iostream>
using namespace std;
struct A
{
    A(){}
    A(const A&){cout<<"copy ctor"<<endl;}
};
int main()
{
    A a1;
    A a2(move(a1)); // copy ctor
    return 0;
}