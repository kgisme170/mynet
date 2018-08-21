#include<type_traits>
using namespace std;
template<int i>
struct place_holder{};
using _1=place_holder<1>;
using _2=place_holder<2>;
template<typename T>
struct is_placeholder:public integral_constant<int,0>{};
template<int i>
struct is_placeholder<place_holder<i> >:public integral_constant<int,i>{};
template<typename T>
int check(){
    return is_placeholder<T>::value;
}
int main(){
    int i=check<_1>();
    return 0;
}
