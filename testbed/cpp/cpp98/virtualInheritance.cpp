#include<iostream>
using namespace std;

struct Base1{ int mi,mj,mk,mh;};//16=4x4
struct Base2{ int ni,nj,nk,nh;};//16

struct Child1:virtual Base1{virtual void f(){}};//16+8(vtable ptr/vptr)
struct Child2:virtual Base2{virtual void f(){}};//16+8
struct Derive1:Child1,Child2{};//16+8(Child1 vptr)+8(Child2 vptr)=32

struct Child3:virtual Base2{virtual void f(){}};
struct Child4:virtual Base2{virtual void f(){}};
struct Derive2:Child3,Child4{};

struct Final:Derive1,Derive2{};//64
int main(){
    cout<<"C1="<<sizeof(Child1)<<endl;
    cout<<"C2="<<sizeof(Child2)<<endl;
    cout<<"C3="<<sizeof(Child3)<<endl;
    cout<<"C4="<<sizeof(Child4)<<endl;
    cout<<"D1="<<sizeof(Derive1)<<endl;
    cout<<"D2="<<sizeof(Derive2)<<endl;
    cout<<"F ="<<sizeof(Final)<<endl;
    return 0;
}
/*
C1=24
C2=24
C3=24
C4=24
D1=48
D2=32
F =64
0x1d38080
0x1d38080
0x1d38088
*/