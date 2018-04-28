#include<cstdio>
using namespace std;
template<typename T,typename R, typename P1, typename P2>
struct closure{
    typedef R (T::*func)(P1 p1,P2 p2);
    T* obj;
    func pf;
    P1 p1;
    P2 p2;
    closure(T* o,func f,P1 _p1,P2 _p2)
        :obj(o),pf(f),p1(_p1),p2(_p2){}
    R Run(){
        return (obj->*pf)(p1,p2);
    }
};
template<typename T,typename R, typename P1, typename P2>
closure<T,R,P1,P2> GetClosure(T* obj,R (T::*pf)(P1,P2), P1 p1, P2 p2){
    return closure<T,R,P1,P2>(obj,pf,p1,p2);
}
struct M1{
    virtual int add(int a,int b){return a+b;}
};
struct M2{
    virtual int add(int a,int b){return (a+b)*2;}
};
struct N:public M1,M2{
    virtual int add(int a,int b){return (a+b)*4;}
};
int main(void){
    M1 obj;
    closure<M1,int,int,int> c=GetClosure(&obj,&M1::add,1,2);
    int r=c.Run();
    printf("%d\n",r);

    N obj2;
    M2* pn=&obj2;
    int (M2::*pm2)(int,int)=&M2::add;
    printf("%d\n",(obj2.*pm2)(1,2));
    printf("%d\n",(pn->*pm2)(1,2));
    
    closure<M2,int,int,int> c2=GetClosure(pn,pm2,1,2);
    int r2=c2.Run();
    printf("%d\n",r2);//打印12
    return 0;
}
