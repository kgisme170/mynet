#include <charconv>
#include <cstdio>

int main()
{
    int i = 126;
    char buf[20];
    auto [p, e] = std::to_chars(buf, buf + 19, i, 2);
    std::puts(e);
    *p = '\0';
    std::puts(buf);
}