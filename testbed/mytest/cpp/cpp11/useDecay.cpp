#include<iostream>
#include<map>
#include<string>
using namespace std;
template<class T>
void func1(T&& param) {
    if (std::is_same<T,int>::value) {
        cout << "int\n";
    }
    else {
        cout << "not int\n";
    }
}
template<class T>
void func2(T&& param) {
    if (std::is_same<typename std::decay<T>::type, int>::value) {
        cout << "int\n";
    }
    else {
        cout << "not int\n";
    }
}
int main() {
    std::pair<std::string, int> p = make_pair("bar", 0);
    int v = 3;
    func1(v);  //prints "param is not an int"!!!!
    func2(v);
    return 0;
}