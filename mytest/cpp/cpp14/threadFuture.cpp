#include<thread>
#include<iostream>
#include<future>
#include<chrono>
using namespace std;
int f(int a, int b){return a+b;}
int main() {
    thread t1(f, 1, 2);
    t1.join();

    packaged_task<int()> task([]() { return 1; });
    future<int> f1 = task.get_future();
    thread(move(task)).detach();

    future<int> f2 = async(launch::async, []() { return 2; });
    promise<int> p;
    future<int> f3 = p.get_future();
    thread([&p] { p.set_value_at_thread_exit(3); }).detach();

    f1.wait();
    f2.wait_for(chrono::seconds(2));
    f3.wait();
    cout << f1.get() << f2.get() << f3.get() << endl;

    return 0;
}