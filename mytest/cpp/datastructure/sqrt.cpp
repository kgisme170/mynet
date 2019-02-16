#include <iostream>
using namespace std;
/*功能:
 *计算一个数的平方根，精度有限定
 */
float fabs(float f){return f>0? f:-f;};
float sqrt(float A, float e/*epsilon*/) {
    float p = A / 2;
    while (fabs(p * p - A) > e) {
        p = (p + A / p) / 2;
    }
    return p;
}
float sqrt(float A, float e,float p) {//递归版本
    if (fabs(p * p - A) < e)return p;
    else return sqrt(A, e, (p + A / p) / 2);
}
int main() {
    cout << sqrt(20, 0.01) << endl;
    cout << sqrt(20, 0.01, 4) << endl;
    return 0;
}