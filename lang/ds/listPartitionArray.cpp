#include <iostream>
using namespace std;
/*功能:
 *把一个数组，奇数放到前面，偶数放到后面
 */
void partition(int buf[], size_t len){
    int i=0;
    int j=len-1;
    while(i<j){
        while(buf[i]%2==1)++i;//找到偶数
        while(buf[j]%2==0)--j;//找到奇数
        int tmp = buf[i];
        buf[i]=buf[j];
        buf[j]=tmp;
    }
}
int main(){
    int buf[]={2,2,3,1,4,2,5,5,6,7};
    partition(buf, sizeof(buf)/sizeof(int));
    for(size_t i=0;i<sizeof(buf)/sizeof(int);++i){
        cout<<buf[i]<<',';
    }
    return 0;
}
