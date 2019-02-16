#include<iostream>
using namespace std;
namespace m {
    class my {
    public:
        enum A {
            u = 1,
            v = 2,
            w = 3
        };

        static A f(A a) {
            return (A) (a + A::u);
        }
    };
}
int main() {
    using namespace m;
    my::A r = my::f(my::u);
    return 0;
}