template <typename Base, typename Derived>
struct my_is_base_of {
    struct Yes {
        char _;
    };
    struct No {
        char _[2];
    };

    static Yes _test(const Base *);

    static No _test(void *);

    static const bool value = sizeof(_test((Derived *) 0)) == sizeof(Yes);
};

template <typename Base, typename Derived>
bool is_base_derived() {
    return my_is_base_of<Base, Derived>::value;
}

#include<iostream>
using namespace std;
struct ExceptionBase{};
struct MyException : ExceptionBase {};
struct C {};

int main() {
    cout << boolalpha;
    cout << "a2b: " << is_base_derived<ExceptionBase, MyException>() << '\n';
    cout << "a2b: " << is_base_derived<ExceptionBase, ExceptionBase>() << '\n';
    cout << "a2b: " << is_base_derived<MyException, ExceptionBase>() << '\n';
    cout << "c2b: " << is_base_derived<ExceptionBase, C>() << '\n';
    return 0;
}
/*
输出是
a2b: true
a2b: true
a2b: false
c2b: false
*/
