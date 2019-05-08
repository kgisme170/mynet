#include<iostream>
using namespace std;

template<typename Elem, size_t len>
struct Array {
    Elem buf[len];

    Array() {
        for (size_t i = 0; i < len; ++i)
            buf[i] = i;
    }

    void print() {
        for (size_t i = 0; i < len; ++i)
            cout << buf[i] << ',';
        cout << '\n';
    }

    Array operator+(const Array &a) {
        Array ret;
        for (size_t i = 0; i < len; ++i) {
            ret.buf[i] = buf[i] + a.buf[i];
        }
        return ret;
    }
};
int main() {
    Array<int, 3> a1, a2, a3, a4;
    a1.print();//0,1,2,
    Array<int, 3> a5 = a1 + a2 + a3 + a4;
    a5.print();//0,4,8,
    return 0;
}