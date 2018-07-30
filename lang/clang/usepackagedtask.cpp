#include<chrono>
#include<future>
#include<iostream>
#include<thread>
#include<type_traits>
using namespace std;
int main(){
    packaged_task<int()> task(
        [](){
        this_thread::sleep_for(chrono::microseconds(1000));
        return 6;}
    );
    thread t(ref(task));
    future<int> f = task.get_future();
    cout<<f.get()<<'\n';
    t.join();
    return 0;
}
