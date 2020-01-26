#include <chrono>
#include <ctime>
#include <iostream>
using namespace std;
using namespace std::chrono;
time_point<system_clock> midnight() {
	auto tt = system_clock::to_time_t(system_clock::now());
	struct tm* ptm = localtime(&tt);
	++ptm->tm_mday;
	ptm->tm_hour = 0;
	ptm->tm_min = 0;
	ptm->tm_sec = 0;
	return system_clock::from_time_t(mktime(ptm));
}
int main() {
    return 0;
}
