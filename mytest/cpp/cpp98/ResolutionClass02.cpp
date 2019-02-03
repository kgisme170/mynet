#include<stdio.h>
void f(short){printf("short\n");}
struct NameLookup{
    static void f(int i){
        short s=i;
        f(s); // 递归调用自己，abort()
    }
};
int main(){
    int i=0;
    NameLookup::f(i);
    return 0;
}