#include<stdio.h>
struct POD {
    int i;
    int m;
};
struct Static_struct {
    constexpr static POD pod = {2, 3};
};
int main() {
    printf("%d\n", Static_struct::pod.i);
    return 0;
}