#include <iostream>
#include <thread>
#include <shared_mutex>
#include <mutex>
using namespace std;
/*功能: 测试读写锁类型的变量：允许多个读，但是只有一个写*/

class Counter {
    int value;
    shared_mutex m;
public:
    Counter(int v) : value(v) {}

    void Increase() {
        unique_lock <shared_mutex> _(m);
        ++value;
    }

    void Decrease() {
        unique_lock <shared_mutex> _(m);
        --value;
    }

    int Get() {
        shared_lock <shared_mutex> _(m);
        return value;
    }
} c(0);
void Worker() {
    for (size_t i = 0; i < 4; ++i) {
        c.Increase();
    }
    cout << c.Get() << '\n';
}
int main() {
    thread tc(Worker);
    thread tp(Worker);
    tc.join();
    tp.join();
    return 0;
}