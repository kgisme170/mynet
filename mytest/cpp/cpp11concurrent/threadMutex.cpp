#include <iostream>
#include <string>
#include <thread>
#include <functional>
#include <mutex>
using namespace std;
/*功能: 测试线程和mutex的功能*/
mutex m;
int main() {
    thread([]() { cout << "hello\n"; }).join();
    thread([](const string &s) { cout << "hello " << s << '\n'; }, "world").join();
    struct Hello {
        void operator()(const string &msg) { cout << "hello " << msg << '\n'; }
    } hello;
    thread(hello, "kitty").join();
    thread(ref(hello), "my").join();

    struct Hi {
        Hi(const string &msg) {
            thread(bind(&Hi::tf, this, msg)).join();
        }

        void tf(const string &msg) { cout << "hi " << msg << '\n'; }
    } hi("John");
    cout << boolalpha;
    {
        size_t count = 0;
        thread t[10];
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i] = thread([&]() {
                m.lock();
                ++count;
                m.unlock();
            });
        }
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i].join();
        }
        cout << "count=" << count << '\n';
    }
    {
        size_t count = 0;
        thread t[10];
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i] = thread([&]() {
                lock_guard <mutex> _(m);
                ++count;
            });
        }
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i].join();
        }
        cout << "count=" << count << '\n';
    }
    {
        thread t[10];//对于输出流没有保护
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i] = thread([=]() { cout << "test " << i << ','; });
        }
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i].join();
        }
        cout << '\n';
    }
    {
        thread t[10];//对于输出流有保护
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i] = thread([=]() {
                unique_lock <mutex> _(m);
                cout << "test " << i << ',';
            });
        }
        for (size_t i = 0; i < sizeof(t) / sizeof(thread); ++i) {
            t[i].join();
        }
        cout << '\n';
    }
    return 0;
}