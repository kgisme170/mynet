#include <initializer_list>
using namespace std;
template<class T>
void f(std::initializer_list<T>&&t){}//显示指定形参是initializer_list<T>这个
int main() {
    f({1, 2, 3});
    return 0;
}