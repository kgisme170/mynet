#include<cstdlib>
#include<cstring>
using namespace std;
struct A {
    char *msg;

    A() : msg(new char[10]) {}

    ~A() { delete[] msg; }

    A(const A &a) : msg(new char[10]) {
        memcpy(msg, a.msg, 10);
    }

    A &operator=(const A &a) {
        memcpy(msg, a.msg, 10);
        return *this;
    }

    A(A &&a) : msg(a.msg) {
        a.msg = nullptr;//移动，就是要从一个对象当中剥夺资源
    }
};
int main() {
    A a;
    return 0;
}