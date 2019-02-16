#include <iostream>
using namespace std;
/*功能: base 10 to base 2
 *将一个十进制整数N转换成一个二进制数，二进制数用队列表示
 */
int bin[1024];
int tmp[1024];
void f(int base10) {
    size_t idx = 0;
    size_t count = 0;
    while (base10) {
        tmp[idx++] = base10 % 2;
        base10 /= 2;
        ++count;
    }//得到的bin数组是个反序
    cout << count << endl;
    for (size_t i = 0; i < count; ++i) {
        bin[i] = tmp[count - i - 1];
        cout << bin[i] << ',';
    }
}
int main() {
    f(10);
    return 0;
}