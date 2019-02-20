#include<string>
#include<algorithm>
#include<vector>
#include<cstdlib>
#include<cstdio>
#include<iostream>
#include<ctime>
using namespace std;
string randomString() {
    int iBuf[10];
    for (size_t i = 0; i < 10; ++i) {
        double rand0to1 = (double) rand() / RAND_MAX;
        iBuf[i] = (char) rand0to1 * 92 + 33;
    }
    char ret[7];
    snprintf(ret, 7, "%c%c%c%c%c\n",
             iBuf[0], iBuf[1], iBuf[2], iBuf[3], iBuf[4]);
    return ret;
}
int main() {
    srand(time(NULL));
    const size_t scale = 102400000;
    vector <string> vs;
    vs.reserve(scale);
    for (size_t i = 0; i < scale; ++i) {
        vs.push_back(randomString());
    }
    cout << vs.size() << "结束构造\n";
    clock_t begin = clock();
    sort(vs.begin(), vs.end());
    clock_t end = clock();
    double duration = (double) (end - begin) / CLOCKS_PER_SEC;
    cout << "sort " << scale << "个元素耗时" << duration << "秒\n";
    return 0;
}
