#include<iostream>
using namespace std;
struct S1{
    int i;
    //S1():i(2){}
    S1(int _i):i(_i){cout<<"S1:"<<i<<"\n";}
};
struct S2:virtual S1{
    //S2(){}
    S2(int _i):S1(_i){cout<<"S2:"<<i<<"\n";}
};
struct S3:virtual S1{
    //S3(){}
    S3(int _i):S1(_i){cout<<"S3:"<<i<<"\n";}
};
struct S4:S2,S3{
    //S4(){}
    //如果S4(int)的初始化列表里面不指定S1(int)，那么会使用S1()
    S4(int _i):S3(_i),S2(_i),S1(_i){cout<<"S4:"<<i<<"\n";}
};
int main(){
    S4 obj(3);
    return 0;
}
