#include <iostream>
using namespace std;
/*功能:
 *将一个数组左移或者右移若干个元素
 *左移方法是将数组的位移位置作为操作点，左侧逆置，右侧逆置，然后整体逆置
 *右移方法是将数组的位移位置作为操作点，右侧逆置，左侧逆置，然后整体逆置
 */
typedef int T;
void reverse(int* l, size_t size){
    size_t i=0;
    size_t j=size-1;
    int temp;
    for(;i<j;++i,--j){
        temp=l[i];
        l[i]=l[j];
        l[j]=temp;
    }
}
void lshift(int* l, size_t size, size_t count){
    reverse(l, count);
    reverse(l+count, size-count);
    reverse(l, size);
}
void rshift(int* l, size_t size, size_t count){
    reverse(l, size-count);
    reverse(l+size-count,count);
    reverse(l, size);
}
int main()
{
    int buf[]={2,-1,7,3,9,5,6};
    rshift(buf, 7, 2);
    for(size_t i=0;i<7;++i){
        cout<<buf[i]<<',';
    }
    return 0;
}
