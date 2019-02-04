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

template<typename T>
T sumAll(T&& value){
    return value;
}

template<typename Head, typename...Tail>
Head sumAll(Head&& h, Tail&&...tail){
    return h+sumAll(forward<Tail>(tail)...);
}

int main()
{
    double d = 2.3;
    printLine(78,"hi",d);
    printLine("hi",d,'a');
    printLine(d,'a');
    printLine('a');

    cout<<'\n'<<sumAll(2L,'a',3)<<endl;
    return 0;
}