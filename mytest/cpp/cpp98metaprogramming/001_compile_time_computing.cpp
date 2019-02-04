#include<iostream>
using namespace std;

template<int i>
int Multiply(){return i*Multiply<i-1>();}
template<>
int Multiply<1>(){return 1;}

template<typename T,size_t n>
size_t countof(T (&buf)[n]){
    return n;
}

int main() {
    cout<<Multiply<10>()<<endl;
    int buf[5];
    cout<<countof(buf)<<endl;
    return 0;
}