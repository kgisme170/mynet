#include <iostream>
using namespace std;
/*功能:
 *把一个数组，非0元素移动到前端
 */
size_t partition(int buf[], size_t len){
    size_t count = 0;
    for(size_t i=0;i<len;++i){
        if(buf[i]!=0){
            if(i!=count)buf[count]=buf[i];
            ++count;
        }else{
            continue;
        }
    }
    return count;
}
int main(){
    int buf[]={2,0,0,1,0,2,0,7};
    size_t count = partition(buf, sizeof(buf)/sizeof(int));
    for(size_t i=0;i<count;++i){
        cout<<buf[i]<<',';
    }
    return 0;
}
