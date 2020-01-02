#include<string>
#include<iostream>
#include<vector>
#include<ctime>
using namespace std;
vector<string> vs;
string hello="helloworld...................longstring";
int main(int argc,char* argv[]) {
    vs.reserve(10240000);
    clock_t begin = clock();
    for (size_t i = 0; i < 10240000; ++i) {
        vs.push_back(hello + "longtail..................." + "another string");
    }
    clock_t end = clock();
    double duration = (double) (end - begin) / CLOCKS_PER_SEC;
    cout << "vector 元素个数="
         << vs.size()
         << ", 时间="
         << duration
         << "秒\n";
    return 0;
}