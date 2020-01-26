#include<chrono>
#include<future>
#include<iostream>
using namespace std;
using namespace std::chrono;
int main(){
	promise<void> p, p1, p2;
	// shared_future<void> sf(p.get_future());
	auto sf = p.get_future().share();
	time_point<high_resolution_clock> from;
	auto f1 = [&, sf]()->duration<double, milli>{
		p1.set_value();
		sf.wait();
		return chrono::high_resolution_clock::now() - from;
	};
	auto f2 = [&, sf]()->duration<double, milli>{
		p2.set_value();
		sf.wait();
		return chrono::high_resolution_clock::now() - from;
	};
	auto ret1 = async(launch::async, f1);
	auto ret2 = async(launch::async, f2);
	p1.get_future().wait();
	p2.get_future().wait();
	from = high_resolution_clock::now();
	p.set_value();
	cout << "fun1 signal = " << ret1.get().count() << "ms\n";
	cout << "fun2 signal = " << ret2.get().count() << "ms\n";
	return 0;
}
