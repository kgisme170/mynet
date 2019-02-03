#include<stdio.h>
#include<vector>
using namespace std;
class M{
public:
    M(){}
    M(const M&){printf("M拷贝\n");}
    M(M&&){printf("M移动\n");}
};
class N{
public:
    N(){}
    N(const N&){printf("N拷贝\n");}
    N(N&&)noexcept{printf("N移动\n");}
};
int main(){
    vector<M> vm(2);
    vm.resize(100);
    printf("---------------\n");
    vector<N> vn(2);
    vn.resize(100);
    return 0;
}

//打印输出:
//M拷贝
//M拷贝
//---------------
//N移动
//N移动