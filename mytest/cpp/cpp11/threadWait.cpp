#include <thread>
#include <iostream>
#include <chrono>
using namespace std;
mutex mtx;
condition_variable cv;
bool has_signalled{false};

void th_function(){
    auto lck = std::unique_lock<std::mutex>{mtx};
    // loop to protect against spurious wakeups
    while (!has_signalled) {
        // sleep
        cv.wait(lck);
    }
    cout << "Thread has been signalled\n";
}
int main(){
    auto th = std::thread{th_function};
    // sleep for 2 seconds
    std::this_thread::sleep_for(std::chrono::seconds(2));
    // signal and change the variable
    {
        std::lock_guard<std::mutex> lck{mtx};
        has_signalled = true;
    }
    // signal
    cv.notify_one();
    th.join();
    return 0;
}
