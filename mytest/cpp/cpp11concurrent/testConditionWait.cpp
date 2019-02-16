#include <unistd.h>
#include <iostream>
#include <thread>
#include <mutex>
#include <condition_variable>
using namespace std;

mutex m;
condition_variable cv;
bool ready = false;
bool processed = false;
void tf() {
    cout << "sub waits\n";
    unique_lock <mutex> lk(m);//unique_lock other types?
    cv.wait(lk, []() { return ready; });
    cout << "sub notified" << endl;
    sleep(1);
    processed = true;
    cout << "sub notifies main\n";
    cv.notify_one();
}

int main() {
    thread sub(tf);
    sleep(1);
    cout << "main notifies sub\n";
    cv.notify_one(); // tf still waits for 'ready'
    sleep(1);
    ready = true;
    cout << "main notifies sub\n";
    cv.notify_one(); // needs another notify to check cv out of loop
    sub.join();

    cout << "main waits\n";
    unique_lock <mutex> lk(m);
    cv.wait(lk, []() { return processed; });
    cout << "main notified\n";
    return 0;
}