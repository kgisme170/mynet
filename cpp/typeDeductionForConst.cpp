#include<stdio.h>
struct M{
    M(){}
    void operator+(const M&){printf("1\n");}
    void operator+(const M&)const{printf("2\n");}
    void operator+(M&){printf("3\n");}
    void operator+(M&)const{printf("4\n");}
};
int main(){
    const M m1,m2;
    M m3,m4;
    m1+m2;//2
    m1+m3;//4
    m3+m1;//1
    m3+m4;//3
    return 0;
}
