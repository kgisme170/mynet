#include<iostream>
#include<unistd.h>
using namespace std;
int main() {
    char buf[128];
    readlink("/proc/self/exe", buf, sizeof(buf));
    cout << buf << endl;
    return 0;
}