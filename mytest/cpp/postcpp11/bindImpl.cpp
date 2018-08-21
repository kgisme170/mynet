#include<cstdio>
#include<tuple>
using namespace std;
template<size_t index, typename Head, typename...Tail>
struct typeList
{
    typedef typename typeList<index-1, Tail...>::type type;
    static decltype(auto) arg_at(Head h, tuple<Tail...> tail){
        return get<index-1>(tail);
    }
};
template<typename Head, typename...Tail>
struct typeList<0, Head, Tail...>
{
    typedef Head type;
    static Head arg_at(Head h, tuple<Tail...> tail){
        return h;
    }
};
template<int i>
struct place_holder{};
using PH1=place_holder<1>;
PH1 _1;
using PH2=place_holder<2>;
PH2 _2;

template<typename T>
struct is_placeholder:public integral_constant<int,0>{};
template<int i>
struct is_placeholder<place_holder<i> >:public integral_constant<int,i>{};

template<typename R, typename F, typename T1, typename T2, typename T3>
struct bind_3_impl{
    F _pf;
    T1 _t1;
    T2 _t2;
    T3 _t3;
    bind_3_impl(F pf, T1 t1, T2 t2, T3 t3):
        _pf(pf),_t1(t1),_t2(t2),_t3(t3){}
    template<typename...Args>
    R operator()(Args...args){
        tuple<Args...> argList(args...);
        return(*_pf)(
            typeList<is_placeholder<T1>::value, T1, Args...>::arg_at(_t1, argList),
            typeList<is_placeholder<T2>::value, T2, Args...>::arg_at(_t2, argList),
            typeList<is_placeholder<T3>::value, T3, Args...>::arg_at(_t3, argList)            
        );
    }
};
template<typename R, typename F, typename T1, typename T2, typename T3>
bind_3_impl<R,F,T1,T2,T3> bind_3(F pf, T1 t1, T2 t2, T3 t3){
    return bind_3_impl<R,F,T1,T2,T3>(pf, t1, t2, t3);
}
void test(char i,char j,char k){printf("%c,%c,%c",i,j,k);}
int main(){
    typeList<2,int,short,char>::type x='a';
    bind_3<void>(test, 'a', _1, _2)('b','c');
    bind_3<void>(&test, _1, 'y', _2)('x','z');
    return 0;
}
