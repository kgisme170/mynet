#include <iostream>
#include <cstdlib>
using namespace std;
/*功能:
 *读入一个字符串数组,解析其中的数字和操作符,结算出结果,不考虑运算符的优先级
 *简单起见,每个字符都是一个数字或者操作数
 */
int stack[1024];
int idx=-1;
void op(char c){
    if (idx<1){//少于2个元素
        cout<<"栈已经到底，无法取出两个操作数\n";
        exit(1);
    }
    int i=stack[idx--];
    int j=stack[idx--];
    if(c=='+'){
        stack[++idx]=i+j;
    }else if(c=='-'){
        stack[++idx]=i-j;
    }else if(c=='*'){
        stack[++idx]=i*j;
    }else if(c=='/'){
        stack[++idx]=i/j;
    }
}
int exp(char* buf, size_t len){
    for(size_t i=0;i<len;++i){
        char c = buf[i];
        if(c>'0' && c<'9'){
            //数字,直接入栈
            stack[++idx]=c-'0';
        }else{
            op(c);
        }
    }
    return stack[0];
}
int main(){
    char buf[]={'2','3','+', '5','*'};
    cout<<exp(buf,sizeof(buf)/sizeof(char));
    return 0;
}
