#include<iostream>
using namespace std;
/*
template<typename Elem, size_t len>
struct Array{
    Elem buf[len];
    Array(){
        for(size_t i=0;i<len;++i)
            buf[i]=i;
    }
    void print(){
        for(size_t i=0;i<len;++i)
            cout<<buf[i]<<',';
        cout<<'\n';
    }
    Array operator+(const Array& a){
        Array ret;
        for(size_t i=0;i<len;++i){
            ret.buf[i]=buf[i]+a.buf[i];
        }
        return ret;
    }
};
int main(){
    Array<int, 3> a1,a2,a3,a4;
    a1.print();//0,1,2,
    Array<int, 3> a5=a1+a2+a3+a4;
    a5.print();//0,4,8,
    return 0;
}
*/
/*
template<bool b>
struct STATIC_ASSERT_TRUE{};
template<>
struct STATIC_ASSERT_TRUE<true>{static int i;};
template<bool b>
bool TEST_TRUE(){return sizeof(STATIC_ASSERT_TRUE<b>::i)==sizeof(int);}

template<int i>
int Multiply(){return i*Multiply<i-1>();}

template<>
int Multiply<1>(){return 1;}

template<typename T,size_t n>
size_t countof(T (&buf)[n]){
    return n;
}
*/
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
int main(){
    ArrayExp<int,3> a1,a2,a3,a4;
    a1.print();//0,1,2,
    ArrayExp<int,3>a5(a1+a2+a3+a4);
    a5.print();//0,4,8,
    return 0;
}
