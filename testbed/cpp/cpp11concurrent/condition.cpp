#include <iostream>
#include <thread>
#include <condition_variable>
#include <mutex>
#include <chrono>
using namespace std;
mutex m;
condition_variable c;
bool ok=false;
int main() {
    thread t([]() {
        unique_lock <mutex> _(m);
        c.wait(_, []() { return ok; });
        cout << "等待主线程完成\n";
        this_thread::sleep_for(chrono::seconds(10));
        cout << "辅助线程睡眠完成\n";
        c.notify_one();
    });
    this_thread::sleep_for(chrono::seconds(1));
    ok = true;
    c.notify_one();
    cout << "主线程等待\n";
    unique_lock <mutex> _(m);
    this_thread::sleep_for(chrono::seconds(1));
    c.wait(_, [] { return true; });
    t.join();
    return 0;
}
