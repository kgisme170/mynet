#include<utility>
#include<stdio.h>
struct M{
    M(){}
    M(M&&)     {printf("移动\n");}
    M(const M&){printf("拷贝\n");}
    template<class T>
    M(T&&){printf("调用贪婪版本\n");}
};
struct N:M{
    N(){}
    N(N&&n)     :M(std::move(n)){printf("移动\n");}
    N(const N&n):M(n)           {printf("拷贝\n");}
    template<class T>
    N(T&&){printf("调用贪婪版本\n");}
};
int main(){
    const M obj1;
    M obj2(obj1);
    printf("-----------\n");
    M obj3;
    M obj4(std::move(obj3));
    return 0;
}
//打印输出:
//拷贝
//-----------
//移动