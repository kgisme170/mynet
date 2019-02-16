#include<stdio.h>
struct Static_struct {
    static int m;
};
int Static_struct::m=1;
int main() {
    printf("%d\n", Static_struct::m);
    return 0;
}