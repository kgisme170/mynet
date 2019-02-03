#include<stdio.h>
struct M{
    M(int _i,char _c):i(_i),c(_c){}
    int i;
    char c;
    void print()& {printf("左值对象\n");}
    void print()&&{printf("右值对象\n");}
};
int main(){
    M obj(2,'m');
    obj.print();
    printf("--------\n");
    M(3,'n').print();
    return 0;
}
//打印输出:
//左值对象
//--------
//右值对象