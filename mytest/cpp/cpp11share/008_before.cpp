#include<iostream>
using namespace std;
static int i=0;
struct A{
    A(){}
    A(const A&){i+=1;}
};
int main()
{
    A obj;
    A obj2(move(obj));
    cout<<i<<endl; // 1
    return 0;
}