#include <iostream>
#include <string>
using namespace std;
struct Base{
    virtual void f(const string& s="Base"){
        cout<<s<<endl;
    }
};
struct Derived:public Base{
    virtual void f(const string& s="Derived"){
        cout<<s<<endl;
    }
};
int main(int argc, char* argv[])
{
	Base* p = new Derived();
	p->f();
	delete p;
	return 0;
}
