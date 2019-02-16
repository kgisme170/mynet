#include <iostream>
#include <vector>
using namespace std;
/*功能:
 *比较两个顺序表A/B，出去最大公共前缀后，如果剩余元素都是空，则相等
 *如果A为空且B不为空，且A的第一个元素小于B的第一个元素，则A<B，否则A>B
 */
typedef int T;
typedef vector<T> Sqlist;
int compare(const Sqlist& A, const Sqlist& B) {
    size_t s1 = A.size();
    size_t s2 = B.size();
    size_t i = 0;
    while (i < s1 && i < s2) {
        if (A[i] != B[i])break;
        ++i;
    }
    if (i == s1 && i == s2)return 0;//相等
    if (i == s1 && i < s2)return -1;
    if (i < s1 && i < s2 && A[i] < B[i])return -1;
    else return 1;
}
int main() {
    int buf1[] = {2, -1, 7, 3};
    int buf2[] = {2, -1, 7, 3};
    int buf3[] = {2, -1, 7, 3, 4};
    int buf4[] = {2, -1};
    Sqlist s1(buf1, &buf1[4]);
    Sqlist s2(buf2, &buf2[4]);
    Sqlist s3(buf3, &buf3[5]);
    Sqlist s4(buf4, &buf4[2]);
    cout << compare(s1, s2) << '\n';//0
    cout << compare(s1, s3) << '\n';
    cout << compare(s1, s4) << '\n';//1
    return 0;
}