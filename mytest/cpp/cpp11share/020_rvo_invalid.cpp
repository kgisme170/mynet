#include<stdio.h>
class M {
public:
    M(const M &) { printf("拷贝\n"); }

    M() { printf("构造\n"); }

    M(int i) { printf("构造\n"); }
};
M f(int i) {
    if (i == 0) return M();
    else return M(i);
}
M g(int i) {
    M obj;
    if (i == 0) return obj;
    else return M(i);
}
int main(int argc,char*argv[]) {
    M obj1 = f(argc);
    printf("------------\n");
    M obj2 = g(argc);
    return 0;
}

//打印输出:
//构造
//------------
//构造
//构造