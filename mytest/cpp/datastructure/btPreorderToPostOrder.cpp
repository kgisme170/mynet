#include <iostream>
using namespace std;
/*功能: 已知满2叉树的先序遍历，求后序遍历*/
void change(int pre[], int post[], size_t l1, size_t r1, size_t l2, size_t r2) {
    if (l1 > r1)return;
    cout << "change(" << l1 << "," << r1 << "," << l2 << "," << r2 << ")\n";
    post[r2] = pre[l1];
    change(pre, post, l1 + 1, (l1 + r1 + 1) / 2, l2, (l2 + r2 - 1) / 2);//l1向后一个
    change(pre, post, (l1 + r1 + 1) / 2 + 1, r1, (l2 + r2 - 1) / 2 + 1, r2 - 1);//l2向前一个
}
void convert(int pre[], int post[], size_t len) {
    size_t h = 0;
    size_t full = len + 1;
    while (full > 1) {
        full = full / 2;
        ++h;
    }
    cout << "树的高度=" << h << '\n';
    change(pre, post, 0, len - 1, 0, len - 1);
}
int main() {
    const size_t len = 7;
    int pre[len] = {1, 2, 3, 4, 5, 6, 7};
    int post[len];
    convert(pre, post, len);
    for (size_t i = 0; i < len; ++i)cout << post[i] << ',';
    return 0;
}