#include<stdio.h>
#include<vector>
struct M{
    M(int _i,char _c):i(_i),c(_c){printf("构造\n");}
    int i;
    char c;
    M(const M&){printf("拷贝\n");}
    M(M&&)     {printf("移动\n");}
};
int main(){
    std::vector<M> vm;
    vm.emplace_back(M(2,'m'));
    return 0;
}

//打印输出:
//构造
//移动