#include<stdio.h>
struct NameLookup{
    static void f(int i){
        short s=i;
        f(s);
    }
    static void f(short){printf("short\n");}
};
int main(){
    int i=0;
    NameLookup::f(i);//打印short
    return 0;
}