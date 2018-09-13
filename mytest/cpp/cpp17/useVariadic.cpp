#include<utility>
#include<iostream>
using namespace std;
template <std::size_t N, std::size_t... Ix>
bool in_range1() {
   return ((Ix < N) && ...);
}
template <std::size_t N, typename... Ix>
bool in_range2(Ix... ix) {
   return ((ix < N) && ...);
}
template <std::size_t N>
constexpr bool in_range3(std::initializer_list<std::size_t> ix) {
    for(auto i : ix) {
        if(i >= N) return false;
    }
    return true;
}
int main()
{
    cout<<in_range1<10,1,2,30>()<<endl;
    cout<<in_range2<10>(1U,2U,30U)<<endl;
    cout<<in_range3<10>({1,2,30})<<endl;
    return 0;
}