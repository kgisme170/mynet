#include<iostream>
#include<thread>
using namespace std;
struct functor{
    int mi;
    functor():mi(3){}
    ~functor(){
        cout<<"dtor\n";
    }
    void operator()(){
        cout<<"operator()"<<endl;
        ++mi;
        cout<<mi<<endl;
    }
};
int main(){
    auto f=functor();
    cout<<f.mi<<endl;
    thread t(f);
    t.join();

    cout<<f.mi<<endl;
    cout<<"---------\n";
    thread t2{functor()};
    t2.join();
    return 0;
}
