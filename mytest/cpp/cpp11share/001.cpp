#include<stdio.h>
class M {
public:
    M() { printf("构造\n"); }

    M(const M &) { printf("拷贝\n"); }
};
M f() {
    return M();
}
int main() {
    M obj = f();
    return 0;
}