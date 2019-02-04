#include <initializer_list>
#include <iostream>
using namespace std;
struct A{
    A(double i){cout<<"A double\n";}
    A(const initializer_list<double> dlist){cout<<"A init\n";}
};
int main() {
    A a(1.0f);//编译告警或者失败，取决于编译器和编译选项
    return 0;
}