#include<stdio.h>
struct M{
    int m_i;
    M():m_i(2){}
    int GetValue(){return m_i;}
    int&GetRef()  {return m_i;}
};

int main(){
    M obj;
    auto i=obj.GetValue();
    auto j=obj.GetRef();
    auto&k=obj.GetRef();

    ++i;++j;
    printf("%d\n",obj.m_i);//打印2
    ++k;
    printf("%d\n",obj.m_i);//打印3

    return 0;
}
