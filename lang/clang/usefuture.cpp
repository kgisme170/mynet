#include<chrono>
#include<future>
#include<iostream>
using namespace std;
bool isprime(int x){
    for(int i=1;i<x/2;++i){
        if(x%i==0)return false;
    }
    return true;
}
int main(){
    future<bool> f = async(std::launch::async,isprime,2017);
    while(f.wait_for(chrono::microseconds(100))
          != future_status::ready){
        cout<<'.';
    }
    cout<<boolalpha;
    cout<<f.get()<<'\n';
    future<bool> f2 = async(std::launch::async,isprime,20182017);
    f2.wait();
    cout<<f2.get()<<'\n';
    return 0;
}
