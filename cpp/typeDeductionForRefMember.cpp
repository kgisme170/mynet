#include<stdio.h>
struct S{
    int i;
    int j;
    int k;
};
struct R{
    R(const S&obj):s(obj){}
    const S& s;
};
int main(){
    S obj={1,2,3};
    R r(obj);
    printf("%lu\n",sizeof(r));
    return 0;
}
