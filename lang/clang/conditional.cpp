#include <iostream>
#include <thread>
#include <condition_variable>
#include <mutex>
using namespace std;
/*功能: 测试线程和mutex的功能*/

mutex m;
condition_variable c;
bool ok=false;
int main(){
    thread t([](){
        unique_lock<mutex> _(m);
        c.wait(_, [](){return ok;});
        cout<<"等待主线程完成\n";
        this_thread::sleep_for(10s);
        cout<<"辅助线程睡眠完成\n";
        c.notify_one();
    });

    this_thread::sleep_for(1s);
    ok=true;
    c.notify_one();

    cout<<"主线程等待\n";
    unique_lock<mutex> _(m);
    this_thread::sleep_for(1s);
    c.wait(_, []{return true;});
    t.join();
    return 0;
}
