#include <iostream>
#include <string>
using namespace std;
/*功能:
 *设计KMP算法，分别使用next和nextval数组
 */
struct KMP{
    string s;//子串
    string m;//主串
    int next[1024];
    KMP(const string& _s, const string& _m):s(_s),m(_m){}
    virtual void getnext(){
        size_t j=1,t=0;
        next[1]=0;
        while(j<s.length()){
            if(t==0||s[j-1]==s[t-1]){
                ++j;
                ++t;
                next[j]=t;
            }else{
                t=next[t];//回溯模式串
            }
        }
    }
    size_t op(){
        getnext();
        size_t i=1,j=1;
        while(i<=s.length() && j<=m.length()){
            if(i==0 || s[i-1] == m[j-1]){//i是子串下标
                ++i;
                ++j;
            }else{
                i = next[i];
            }
        }
        if(i>s.length())return j-s.length();
        else return -1;
    }
    void print(){
        getnext();
        for(size_t i=0;i<s.length();++i){
            cout<<next[i+1]<<',';
        }
        cout<<'\n';
    }
};
struct KMP2:KMP{//getnextval
    KMP2(const string& _s, const string& _m):KMP(_s,_m){}
    void getnext(){
        size_t j=1,t=0;
        next[1]=0;
        while(j<s.length()){
            if(t==0||s[j-1]==s[t-1]){
                ++j;
                ++t;
                if(s[j-1]!=s[t-1]){
                    next[j]=t;
                }else{
                    next[j]=next[t];
                }
            }else{
                t=next[t];//回溯模式串
            }
        }
    }
};
int main(){
    KMP k1("abab", "cccabcababcc");
    cout<<k1.op()<<endl;
    KMP k2("ababaaababaa", "");
    k2.print();

    KMP2 k3("ababaaababaa", "");
    k3.print();
    return 0;
}
