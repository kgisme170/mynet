#include<functional>
#include<iostream>
#include<type_traits>
using namespace std;
struct C{
    int m_i = 2;
    auto get1(){return ref(m_i);}
    auto& get2(){return (m_i);}
};
int g(){
    C s;
    s.get1().get() = 3;
    cout<<s.m_i<<endl;

    s.get2()=4;
    cout<<s.m_i<<endl;
    return 0;
}

template <class F, class R = typename result_of<F&()>::type>
R call(F& f) { return f(); }
struct SS {
  double operator()()& {return 0.0;}
  void operator()()&& { }
};

struct S {
    double operator()(){return 0.0;}
};
int f(){return 1;}
int main()
{
    g();
    S obj;
    call(obj);//ok
    call(f);//error!
    return 0;
}