#include <iostream>
using namespace std;
/*功能:
 *把一个数组，比头元素小的放在前面，比头元素大的放在后面
 *该算法是快排的一部分
 */
void partition(int* elements, size_t len) {
    int temp = elements[0];//头元素，用于比较
    int *begin = elements;
    int *end = &elements[len - 1];
    while (begin != end) {
        while (begin != end && *end >= temp)--end;
        if (begin != end) {
            *begin = *end;
            ++begin;
        }
        while (begin != end && *begin < temp)++begin;
        if (begin != end) {
            *end = *begin;
            ++end;
        }
        *begin = temp;
    }
}
int main() {
    int buf[] = {2, 1, -7, -3, 5, 6, -1};
    partition(buf, sizeof(buf) / sizeof(int));
    for (int i = 0; i < sizeof(buf) / sizeof(int); ++i) {
        cout << buf[i] << endl;
    }
    return 0;
}