#include<cstring>
#include<iostream>
#include<memory>
#include<utility>
#include<vector>
using namespace std;
struct M {
    char *s;

    M(const char *str) : s(new char[20]) { strcpy(s, str); }

    M(const M &m) : s(new char[20]) {
        strcpy(s, m.s);
        ++copies;
    }

    M(M &&m) {
        s = m.s;
        m.s = nullptr;
        ++moves;
    }

    ~M() noexcept {}

    static size_t copies;
    static size_t moves;
};
size_t M::copies=0;
size_t M::moves=0;
struct N {
    char *s;

    N(const char *str) : s(new char[20]) { strcpy(s, str); }

    N(const N &n) : s(new char[20]) {
        strcpy(s, n.s);
        ++copies;
    }

    N(N &&n) {
        s = n.s;
        n.s = nullptr;
        ++moves;
    }

    ~N() {}

    static size_t copies;
    static size_t moves;
};
size_t N::copies=0;
size_t N::moves=0;
int main(int argc,char*argv[]) {
    const size_t count = 10;
    vector <M> vm;
    for (size_t i = 0; i < count; ++i)
        vm.push_back(argv[0]);
    cout << "M.copies=" << M::copies << ",moves=" << M::moves << endl;

    vector <N> vn;
    for (size_t i = 0; i < count; ++i)
        vn.push_back(argv[0]);
    cout << "N.copies=" << N::copies << ",moves=" << N::moves << endl;
    return 0;
}

//打印输出:
//M.copies=0,moves=25
//N.copies=15,moves=10
