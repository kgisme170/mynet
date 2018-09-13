#include<stdio.h>
typedef void (*pf)();
struct C{
	virtual void f(){
		printf("weird\n");
	}
};
int main(){
    C c1;
    C* p=&c1;
    pf* pvtable=(pf*)p;
    pf func1=pvtable[0];
    (*func1)();
    return 0;
}