#include <initializer_list>
#include <iostream>
using namespace std;
struct A{
    A(int i){cout<<"A int\n";}
    A(const initializer_list<int> dlist){cout<<"A init\n";}
};
int main() {
    A a{};//打印A int
    A a{{}};//打印A init
    return 0;
}