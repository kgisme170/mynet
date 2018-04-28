#include<utility>
using namespace std;
struct A{
    int i;
    char c;
};
void f(const A&){}
template<class T>
void g(T&& t)
{
    f(forward<T>(t));
}
int main() {
    A a={1,'@'};
    f({1,'#'});
    //g({1,'@'});//fix: g<A>({1,'@'})
    g<A>({1,'@'});
    return 0;
}
