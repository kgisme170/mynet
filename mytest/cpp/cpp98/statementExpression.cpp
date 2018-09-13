#include<stdio.h>
struct A{
	A(){printf("ctor\n");}
	A(const A&){printf("copy\n");}
	~A(){printf("dtor\n");}
	void f(){printf("f\n");}
};
int main(){
    int i=0;
    int j=({int k=3;++i;})+1;// this line
	A a;
	({a;}).f();
    return 0;
}