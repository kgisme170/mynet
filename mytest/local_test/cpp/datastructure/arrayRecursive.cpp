#include <iostream>
using namespace std;
/*功能:
 *设计递归算法，求解数组的最大值，求和，以及平均值
 */
float max(float A, float B){
    return A>B? A:B;
}
float max(float* A, size_t len){
    return len == 1 ? *A : max(*A, max(A+1, len-1));
}
float sum(float* A, size_t len){
    return len == 1 ? *A : *A + sum(A+1, len-1);
}
float avg(float* A, size_t len){
    return len == 1 ? *A : (*A + avg(A+1, len-1)*(len-1))/len;
}
int main(){
    float buf[]={2,0,8,1,-4,2,0,7};
    cout<<max(buf,sizeof(buf)/sizeof(float))<<'\n';
    cout<<sum(buf,sizeof(buf)/sizeof(float))<<'\n';
    cout<<avg(buf,sizeof(buf)/sizeof(float))<<'\n';
    return 0;
}
