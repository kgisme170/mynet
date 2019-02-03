#include<stdio.h>
struct M{
    M(){}
    M(M&&)     {printf("移动\n");}
    M(const M&){printf("拷贝\n");}
    template<class T>
    M(T&&){printf("调用贪婪版本\n");}
};
int main(){
    M obj1;
    M obj2(obj1);//调用贪婪版本
    return 0;
}