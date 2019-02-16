#include<iostream>
#include<sstream>
#include<functional>
using namespace std;
using namespace std::placeholders;
constexpr long double operator"" _w (long double d){return d;}//瓦
constexpr long double operator"" _mw(long double d){return d/1000;}//毫瓦
struct Watt {
    double d;

    Watt(long double _d) : d(_d) {}
};
Watt operator "" _watt(long double d){return Watt(d);}

struct XYZ {
    XYZ(long double _x, long double _y, long double _z) : x(_x), y(_y), z(_z) {}

    long double x, y, z;
};
XYZ operator "" _xyz(const char* str, size_t len) {
    long double x, y, z;
    stringstream ss(str);
    ss >> x >> y >> z;
    return XYZ(x, y, z);
}
void f(int& i,int& j) {
    ++i;
    ++j;
}
int main() {
    Watt w1 = 17.0_w;
    Watt w2 = 25.1_mw;
    Watt w3 = 39.5_watt;
    cout << w1.d << endl;
    cout << w2.d << endl;
    cout << w3.d << endl;

    XYZ xyz = "12.0 13.0 14.0"_xyz;
    cout << xyz.x << ',' << xyz.y << ',' << xyz.z << endl;
    int i = 0;
    int j = 0;
    bind(f, j, ref(i))(3);
    bind(f, ref(j), i)(4);
    cout << j << j << endl;
    return 0;
}