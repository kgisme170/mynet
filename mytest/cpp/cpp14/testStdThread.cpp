#include<thread>
#include<iostream>
#include<chrono>
#include<mutex>
#include<condition_variable>
using namespace std;
mutex mtx;
condition_variable cv;
bool has_signalled(false);
void th_function() {
    auto lck = std::unique_lock<mutex>{mtx};
    while (!has_signalled) {// loop to protect against spurious wakeups
        cout<<"In while"<<endl;
        cv.wait(lck);
    }
    cout << "Thread has been signalled" << endl;
}
int main() {
    auto th = thread{th_function};
    this_thread::sleep_for(chrono::seconds(3));
    {
        lock_guard<mutex> lck{mtx};
        has_signalled = true;
    }
    cv.notify_one();// signal
    th.join();
}
