#include <iostream>
#include <bitset>
#include <climits>

template<typename T>
void show_binrep(const T& a)
{
    const char* beg = reinterpret_cast<const char*>(&a);
    const char* end = beg + sizeof(a);
    while(beg != end)
        std::cout << std::bitset<CHAR_BIT>(*beg++) << ' ';
    std::cout << '\n';
}
int main()
{
    char a, b;
    short c;
    a = -58;
    b = a >> 3;
    c = -315;
    show_binrep(a);
    show_binrep(b);
    show_binrep(c);
    float f = 3.14;
    show_binrep(f);
}