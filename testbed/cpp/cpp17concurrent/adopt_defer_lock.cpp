#include <mutex>
#include <thread>
#include <iostream>
#include <vector>
#include <functional>
#include <chrono>
#include <string>
using namespace std;
int main(){
	mutex m1;
	{
		scoped_lock sl(m1);
		lock_guard<mutex> g(m1);
	}
	mutex m2;
	{
		lock(m1, m2);
		lock_guard<mutex> lg1(m1, adopt_lock);
		lock_guard<mutex> lg2(m2, adopt_lock);
	}
	unique_lock<mutex> u1(m1, defer_lock);
	unique_lock<mutex> u2(m2, defer_lock);
	lock(m1, m2);
	return 0;
}
