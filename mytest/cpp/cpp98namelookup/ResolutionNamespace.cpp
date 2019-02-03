#include<stdio.h>
void f(short){printf("short\n");}
namespace NameLookup{
    void f(int i){
        short s=i;
        f(s);
    }
};
int main(){
    int i=0;
    NameLookup::f(i);
    return 0;
}