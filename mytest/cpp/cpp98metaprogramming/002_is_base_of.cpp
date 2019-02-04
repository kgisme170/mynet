#include<iostream>
using namespace std;

template <typename Base, typename Derived>
struct my_is_base_of{
    struct Yes{char _;};
    struct No{char _[2];};
    static Yes _test(const Base*);
    static No _test(void*);
    static const bool value=sizeof(_test((Derived*)0))==sizeof(Yes);
};

template <typename Base, typename Derived>
bool is_base_derived()
{
    return my_is_base_of<Base, Derived>::value;
}

struct ExceptionBase{};
struct MyException : ExceptionBase {};
struct C {};

int main()
{
    cout << boolalpha;
    cout << "a2b: " << is_base_derived<ExceptionBase,MyException>() << '\n'; // true
    cout << "a2b: " << is_base_derived<ExceptionBase,ExceptionBase>() << '\n'; // true
    cout << "a2b: " << is_base_derived<MyException,ExceptionBase>() << '\n'; // false
    cout << "c2b: " << is_base_derived<ExceptionBase,C>() << '\n'; // false
    return 0;
}