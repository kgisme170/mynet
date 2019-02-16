#include <iostream>
using namespace std;
/*功能:
 *寻找顺序表(元素是正整数)的主元素
 */
int majority(int* A, size_t size) {
    int c = A[0];
    int count = 1;
    for (size_t i = 1; i < size; ++i) {
        if (c == A[i]) {//相等
            ++count;
        } else {
            --count;
            if (count == 0) {
                c = A[i];
                ++count;
            }
        }
    }
    if (count <= 1)return -1;
    else return c;
}
int main() {
    int buf1[] = {2, -1, 7, 1, 6, 4, 5};
    int buf2[] = {2, -1, 2, 1, 6, 2, 2, 2};
    cout << majority(buf1, sizeof(buf1) / sizeof(int)) << '\n';
    cout << majority(buf2, sizeof(buf2) / sizeof(int)) << '\n';
    return 0;
}