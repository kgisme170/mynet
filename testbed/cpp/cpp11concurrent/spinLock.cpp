#include <atomic>
#include <chrono>
#include <iostream>
#include <mutex>
#include <thread>
using namespace std;
using namespace std::chrono;
atomic_flag spinlock = ATOMIC_FLAG_INIT;
void f(){
	while (spinlock.test_and_set(memory_order_acquire)) {
	}
	cout<<"before sleep\n";	
	this_thread::sleep_for(chrono::seconds(2));
	cout<<"after sleep\n";	
	spinlock.clear(memory_order_release);
}
int main() {
	auto t1 = thread(f);
	auto t2 = thread(f);
	t1.join();
	t2.join();
    return 0;
}
