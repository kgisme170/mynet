#include<chrono>
#include<future>
#include<iostream>
#include<thread>
#include<type_traits>
using namespace std;
int main() {
    promise<int> p;
    thread t([](promise<int> &p) {
        this_thread::sleep_for(chrono::microseconds(1000));
        p.set_value_at_thread_exit(5);
    }, ref(p));
    future<int> f = p.get_future();
    cout << f.get() << '\n';
    t.join();
    return 0;
}