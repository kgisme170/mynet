#include <cstddef>
#include <utility>
using namespace std;
template<class... T>
size_t f(T&&...elem){
    return sizeof...(elem);//C++11的sizeof
}

template<class... T>
size_t countof(T&&){return 1;}
template<class Head,class... Tail>
size_t countof(Head&& h,Tail&&... tail){
    return 1+countof(forward<Tail>(tail)...);
}
int main(){
    return countof(1,2,1,2,1);//5
}