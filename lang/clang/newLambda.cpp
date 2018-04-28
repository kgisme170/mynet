#include<iostream>
using namespace std;
int main(){
    int i=2;
    auto f=[=]()mutable{
        ++i;
        cout<<i<<endl;
        cout<<&i<<endl;
        };
    f();//3
    f();//4, 得到同一个i，共享
    cout<<i<<endl;//2
    f();//5

    auto t=[=](const auto& i){return i;};
    cout<<t(1.0)<<endl;
    cout<<t('a')<<endl;
    return 0;
}
