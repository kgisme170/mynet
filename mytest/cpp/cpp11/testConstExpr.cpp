#include<stdio.h>
#include<iostream>
using namespace std;
constexpr int f(){return 3;}
template<int N> int g(){return N;}
constexpr int pow(int base, int exp)//求次方数
{
    return (exp == 0 ? 1 : base * pow(base, exp - 1));
};
class Point {
public:
   constexpr Point(int xVal) : x(xVal) {}
   constexpr int xValue() const { return x; }
private:
   int x;
};
int main(int argc, char**)
{
    int r1=pow(2,3);   //编译时决定exp,OK
    int r2=pow(2,argc);//运行时决定exp,OK
    g<pow(2,3)>();//OK
    //g<pow(2,argc)>();//错误,pow返回值不是编译时可知,不能用于类型参数N
    //constexpr Point p1(argc);//错误,constexpr变量p1初始化必须用constexpr, 刚才说了
    constexpr Point p(2);
    g<p.xValue()>();
    g<Point(2).xValue()>();
    Point p2(2);
    //g(p2.xValue())>();//错误,p2不是constexpr,xValue()丢失constexpr属性，不能给g()
    const int i=argc;
    //g<i>();//错误,模板函数g()要求类型参数是字面量或者constexpr
    return 0;
}