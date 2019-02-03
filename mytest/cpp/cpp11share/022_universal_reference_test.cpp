#include<stdio.h>
struct M{};
void g(const M&){printf("重载,接收左值引用\n");}
void g(M&&     ){printf("重载,接收右值引用\n");}

template<typename T>
void f(T&& t){
    g(t);
}
int main(){
    M obj;
    f(obj);
    f(M());
    return 0;
}

//打印输出:
//重载,接收左值引用
//重载,接收左值引用