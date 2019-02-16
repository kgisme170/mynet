#include<utility>
//foward类型作为返回，带有value category，简单用auto会丢失
template<class Fun, class... Args>
decltype(auto) Example(Fun fun, Args&&... args) {
    return fun(std::forward<Args>(args)...);
}

template<int i>
struct Int {};

constexpr auto iter(Int<0>) -> Int<0>;

template<int i>
constexpr auto iter(Int<i>) -> decltype(auto)
{ return iter(Int<i-1>{}); }

int main() {
    decltype(iter(Int<10>{})) a;
    return 0;
}