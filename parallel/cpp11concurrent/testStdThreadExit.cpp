#include<thread>
#include<iostream>
#include<exception>
#include<chrono>
using namespace std;
struct S {
    S() { cout << "ctor\n"; }

    ~S() { cout << "dtor\n"; }
};
void th_function() {
    try {
        S obj;
        std::terminate();
    } catch (...) {
        cout << "捕获异常\n";
    }
    cout << "Thread正常退出" << endl;
}

int main() {
    auto th = thread{th_function};
    this_thread::sleep_for(chrono::seconds(1));
    th.join();
    return 0;
}