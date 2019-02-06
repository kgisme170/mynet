#include <initializer_list>
#include <iostream>
using namespace std;
initializer_list<int> f(){return {1,2,3};}
int main() {
    for(auto& e in f()){
        cout<<e<<endl;//打印的可能是随机值
    }
    return 0;
}