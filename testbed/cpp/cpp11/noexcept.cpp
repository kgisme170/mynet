#include<iostream>
#include<vector>
using namespace std;
struct A {
    A() {}

    A(const A &) { cout << "A copy ctor\n"; }

    A(A &&) { cout << "A move ctor\n"; }
};
struct B {
    B() {}

    B(const B &) { cout << "A copy ctor\n"; }

    B(B &&) noexcept { cout << "B move ctor\n"; }
};
template<class T>void f() {
    vector <T> vi(2);
    size_t c = 0;
    cout << "========\n";
    for (size_t i = 0; i < 4; ++i) {
        cout << "begin emplace_back...........\n";
        vi.emplace_back(T());
        cout << "end emplace_back...........\n";
    }
    vi.resize(0);
}
int main() {
    f<A>();
    f<B>();
    return 0;
}