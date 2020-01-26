#include<atomic>
#include<future>
#include<iostream>
using namespace std;
int main(){
	atomic<int> ai;
	auto a1 = async([&]{
		ai.store(3);
	});
	ai.store(2);
	atomic_thread_fence(memory_order_release);
	a1.wait();
	cout<<ai.load()<<endl;
	return 0;
}
