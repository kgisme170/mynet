#include<iostream>
using namespace std;

class My{
    int getInt() const {return 20;}
    short getShort() const {return 3;}
public:
    template <typename T,
              typename std::enable_if<std::is_same<T, int>::value>::type* = nullptr>
    int get() const { return getInt(); }

    template <typename T,
              typename std::enable_if<std::is_same<T, short>::value>::type* = nullptr>
    short get() const { return getShort(); }
};

struct Proxy{
    My const* myOwner;
    Proxy(My const* owner):myOwner(owner){}
    operator int() const { return myOwner->get<int>(); }
    operator short() const { return myOwner->get<short>(); }
};

int main(){
    My m;
    Proxy p(&m);
    int _i = p;
    short _s = p;
    cout<<_i<<","<<_s<<",\n";

    int i = m.get<int>();
    short s = m.get<short>();
    cout<<i<<","<<s<<",\n";
    return 0;
}
