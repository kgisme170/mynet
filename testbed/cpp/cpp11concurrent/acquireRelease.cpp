#include <atomic>
#include <mutex>
#include <thread>
using namespace std;
atomic<bool> x,y;
void write_x_then_y()
{
    x.store(true,memory_order_relaxed);
    y.store(true,memory_order_release); 
}
void read_y_then_x()
{
    while(!y.load(memory_order_acquire)); 
    x.load(memory_order_relaxed);
}
int main() {
    x=false;
    y=false;
    thread a(write_x_then_y);
    thread b(read_y_then_x);
    a.join();
    b.join();
    return 0;
}
