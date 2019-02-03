#include<stdio.h>
void f(short){printf("cfunction short\n");}
template<class T>struct Base{
    void f(int){printf("Base::f() int\n");}
};
template<class T>struct Derived:Base<T>{
    void g(int i){f(i);}
};
int main(){
    Derived<char> obj;
    obj.g(1); //cfunction short
    return 0;
}