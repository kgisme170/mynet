#include <atomic>
#include <chrono>
#include <mutex>
#include <thread>
using namespace std;
std::atomic<bool> x,y;
void write_x_then_y()
{
    x.store(true,std::memory_order_relaxed);
    y.store(true,std::memory_order_release); 
}
void read_y_then_x()
{
    while(!y.load(std::memory_order_acquire)); 
    x.load(std::memory_order_relaxed);
}
int main() {
    x=false;
    y=false;
    std::thread a(write_x_then_y);
    std::thread b(read_y_then_x);
    a.join();
    b.join();
    return 0;
}
