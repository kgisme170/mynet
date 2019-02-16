#include<stdio.h>
class M {
public:
    M(M &&) { printf("移动\n"); } // not printed
    M() { printf("构造\n"); }

    M(const M &) = delete;
};
M f() {
    return M();
}
int main() {
    M obj(f());
    return 0;
}