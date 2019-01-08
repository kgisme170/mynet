#include <iostream>
#include <vector>
#include <iterator>

int main()
{
    std::vector<int> vec{61,62,63,64,65,66,67,68};
    std::fill_n(std::back_inserter(vec), 4, 0);
    for (int n : vec)
        std::cout << n << ' ';
    std::cout << '\n';
    std::copy(vec.begin(), vec.end(), std::ostream_iterator<int>(std::cout, ","));
    std::cout << '\n';
    std::copy(vec.begin(), vec.end(), std::ostream_iterator<char>(std::cout, ","));
}