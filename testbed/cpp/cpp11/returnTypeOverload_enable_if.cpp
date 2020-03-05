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

int main(){
    My m;
    auto i = m.get<int>();
    auto s = m.get<short>();
    cout<<i<<","<<s<<",\n";
    return 0;
}
