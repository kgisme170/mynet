#include<iostream>
using namespace std;

template<typename Elem, size_t len>
struct ArrayExp{
    typedef Elem value_type;
    Elem buf[len];
    ArrayExp(){
        for(size_t i=0;i<len;++i)
            buf[i]=i;
    }
    void print(){
        for(size_t i=0;i<len;++i)
            cout<<buf[i]<<',';
        cout<<'\n';
    }
    const Elem& operator[](size_t i)const{return buf[i];}
    template<typename Expr>
    ArrayExp(const Expr& e){
        for(size_t i=0;i<len;++i){
            buf[i]=e[i];
        }
    }
};

template<typename L,typename R>
struct expr_add{
    const L& l;
    const R& r;
    typedef typename L::value_type value_type;
    expr_add(const L& _l,const R& _r):l(_l),r(_r){}
    const value_type operator[](size_t i)const{
        return l[i]+r[i];
    }
};

template<typename L,typename R>
expr_add<L,R> operator+(const L&l, const R&r){
    return expr_add<L,R>(l,r);
}
// 本质上是构造了一个树形依赖结构，用于运行时懒惰求值
int main(){
    ArrayExp<int,3> a1,a2,a3,a4;
    a1.print();//0,1,2,
    ArrayExp<int,3>a5(a1+a2+a3+a4);
    a5.print();//0,4,8,
    return 0;
}