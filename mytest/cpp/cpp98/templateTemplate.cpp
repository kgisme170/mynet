#include<iostream>
using namespace std;
template<class T>
class My {
public:
    void f() {}

    template<class G>
    void g() const {}
};
template<class T>
void h(const My<T>& m) {
    m.template g<int>();
}
int main() {
    My<char> m;
    m.g<int>();
    h(m);
    return 0;
}