#include<iostream>
using namespace std;
template<typename T>
void printLine(T&& value)
{
    cout << value << endl;
}

template<typename First, typename... Rest>
void printLine(First&& first, Rest&&... rest)
{
    cout << first << ",";
    printLine(forward<Rest>(rest)...);
}

int main()
{
    double d = 2.3;
    printLine(78,"hi",d);
    printLine("hi",d,'a');
    printLine(d,'a');
    printLine('a');
    return 0;
}