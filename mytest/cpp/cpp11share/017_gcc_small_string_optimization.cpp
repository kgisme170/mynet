#include<cstdio>
#include<cstdlib>
#include<string>
using namespace std;
void* operator new(size_t s) {
    printf("分配了%lu字节\n", s);
    return malloc(s);
}
void makeString(int i) {
    printf("i=%d,", i);
    std::string empty;
    for (int s = 0; s < i; ++s) {
        empty += 'a';
    }
}
int main() {
    for (int i = 1; i <= 20; ++i) {
        makeString(i);
    }
    return 0;
}