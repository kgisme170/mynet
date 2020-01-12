#include<iostream>
using namespace std;

struct Base1{ int mi,mj,mk,mh;};
struct Base2{ int ni,nj,nk,nh;};

struct Child1:virtual Base1{virtual void f(){}};
struct Child2:virtual Base1{virtual void f(){}};
struct Derive1:Child1,Child2{};

struct Child3:virtual Base2{virtual void f(){}};
struct Child4:virtual Base2{virtual void f(){}};
struct Derive2:Child3,Child4{};

struct Final:Derive1,Derive2{};
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

