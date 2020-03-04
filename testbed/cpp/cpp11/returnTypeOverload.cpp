#include<iostream>
using namespace std;

template <class T>
int f() {cout<<"fint\n";}

template <class T>
short f() {cout<<"fshort\n";}

struct My{
	int getInt() const {return 20;}
	short getShort() const {return 3;}
};

struct Proxy{
	My const* myOwner;
	Proxy(My const* owner):myOwner(owner){}
	operator int() const {return myOwner->getInt();}
	operator short() const {return myOwner->getShort();}
};

int main(){
	auto i = static_cast<int(*)()>(&f<float>)(); // Call int f<float>
	auto s = static_cast<short(*)()>(&f<float>)(); // Call short f<float>

	My m;
	Proxy p(&m);
	int _i = p;
	short _s = p;
	cout<<_i<<","<<_s<<",\n";
	return 0;
}
