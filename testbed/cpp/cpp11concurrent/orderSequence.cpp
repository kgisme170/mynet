#include <atomic>
#include <chrono>
#include <iostream>
#include <thread>
using namespace std;
atomic<bool> y;
void write()
{
    this_thread::sleep_for(chrono::seconds(2));
    cout<<"end sleep\n";
    y.store(true,memory_order_relaxed);
}
void read()
{
    while(!y.load(memory_order_relaxed)); // spin to wait for atomic::store
    cout<<"end load\n";
}
int main() {
    y = false;
    thread a(write);
    thread b(read);
    a.join();
    b.join();
    cout<<y.load()<<endl;
}
