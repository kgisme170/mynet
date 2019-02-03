#include<cstdio>
#include<utility>
using namespace std;
struct Base{
    Base(){printf("Base\n");}
protected:
    Base(const Base&)=delete;
    Base(Base&&)=delete;
};
struct Derived:Base{
    Derived(){}
    Derived(const Derived&d)=default;
    Derived(Derived&&)=default;
};
int main(){
    Derived d;
    Derived d2=d;
    Derived d3=move(d);
    return 0;
}