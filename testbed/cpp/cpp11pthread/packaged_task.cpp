#include<future>
#include<iostream>
using namespace std;
int main(){
    packaged_task<int(int)> t([](int i){return i+1;});
    auto f = t.get_future();
    cout<<"begin to call t\n";
    t(3);
    cout<<f.get()<<endl;
    return 0;
}
