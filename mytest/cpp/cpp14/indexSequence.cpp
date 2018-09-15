#include <array>

template<int NumVal, int ArrSize>
constexpr void setVal(std::array<int, ArrSize> &constArr) {
        std::get<NumVal>(constArr) = NumVal + 1;
        if(NumVal) setVal<NumVal ? NumVal - 1 : 0, ArrSize>(constArr);
}

template<int ArrSize>
constexpr auto arrRange() -> std::array<int, ArrSize> {
        std::array<int, ArrSize> tmp{};
        setVal<ArrSize - 1, ArrSize>(tmp);
        return tmp;
}

constexpr std::array<int, 100> constArr = arrRange<100>();
/////

template <int... Is> // when called below, Is will be 0 - N
constexpr std::array<int, sizeof...(Is)> make_inc_array_impl(
    std::integer_sequence<int, Is...>) {
  return {{(Is + 1)...}}; // +1 to start at one instead of [0, 1, ...]
}


template <std::size_t N>
constexpr std::array<int, N> make_inc_array() {
  return make_inc_array_impl(std::make_integer_sequence<int, N>{});
}
constexpr auto a = make_inc_array<100>(); // [1, 2, ..., 100]

int main() {
    for(int itr = 0; itr < 100; ++itr) printf("%d ", a[itr]);
}