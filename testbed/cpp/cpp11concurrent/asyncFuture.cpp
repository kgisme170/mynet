#include<future>
#include<iostream>
using namespace std;
struct S{
    int mi;
    S():mi(1){}
    void inc(){++mi;}
    void print(){cout<<mi<<endl;}
};
int main(){
    S s;
    auto f1=async(&S::inc, &s);
    s.print();
    f1.get();
    s.print();
    auto f2=async(&S::inc, s);
    s.print();
    return 0;
}
