#include<future>
#include<iostream>
using namespace std;
int main(){
	promise<int> p;
	auto f = p.get_future();
	auto a1 = async([&p] {
		try {
			throw runtime_error("test promise");
		} catch (...) {
			try {
				p.set_exception(current_exception());
			} catch (...) {}
		}
	});
	a1.wait();
	try {
		cout << f.get() << endl;
	} catch (const exception& e) {
		cout << e.what() << endl;
	}
    return 0;
}
