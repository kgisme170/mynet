#include <chrono>
#include <iostream>
#include <mutex>
#include <thread>
using namespace std;
using namespace std::chrono;
timed_mutex m;
void print_thread_id(int id){
	m.lock();
	cout<<"thread id = "<<id;
	m.unlock();
}
int main() {
	if (m.try_lock()) {
		m.unlock();
	}
	using Ms = chrono::milliseconds;
	if (m.try_lock_for(Ms(20))) {
		m.unlock();
	}
	auto t = thread(print_thread_id, 111);
	t.join();
    return 0;
}
