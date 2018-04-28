#include"apsara/common/closure.h"
#include<string>
using namespace std;
using namespace apsara::common;
using namespace apsara::common::closure;
struct ITask{
    string WriteProfile1(const string&)const{return "abc";}
    string WriteProfile3(const string&,const string&,const string&)const{return "xyz";}
};

template<bool del,typename R,typename T,typename A1 >
class CMethodClosure_1_0 : public Closure<R>
{
    typedef R (T::*Signature)(A1)const;
public:
    CMethodClosure_1_0(T* obj, Signature func, A1 a1)
      : mObj(obj),
        mFunc(func),
        mA1(a1)
    {}
    ~CMethodClosure_1_0() {
        mObj = 0;
        mFunc = 0;
    }
    virtual R Run() {
        SelfDeleter<del, ClosureBase> deleter(this);
        return (mObj->*mFunc)(mA1);
    }
    virtual bool IsAutoDelete() const {
        return del;
    }
private:
    T* mObj;
    Signature mFunc;
    A1 mA1;
};

template<bool del,typename R,typename T,typename A1,typename A2,typename A3 >
class CMethodClosure_3_0 : public Closure<R>
{
    typedef R (T::*Signature)(A1, A2, A3)const;
public:
    CMethodClosure_3_0(T* obj, Signature func, A1 a1, A2 a2, A3 a3)
      : mObj(obj),
        mFunc(func),
        mA1(a1),
        mA2(a2),
        mA3(a3)
    {}
    ~CMethodClosure_3_0() {
        mObj = 0;
        mFunc = 0;
    }
    virtual R Run() {
        SelfDeleter<del, ClosureBase> deleter(this);
        return (mObj->*mFunc)(mA1, mA2, mA3);
    }
    virtual bool IsAutoDelete() const {
        return del;
    }
private:
    T* mObj;
    Signature mFunc;
    A1 mA1;
    A2 mA2;
    A3 mA3;
};

template<typename R, typename T, typename A1>
Closure<R>* CNewClosure1(T* obj, R(T::*func)(const A1&)const, const A1& a1) {
    return new CMethodClosure_1_0<true, R, T, const A1&>(obj, func, a1);
}
template<typename R, typename T, typename A1, typename A2, typename A3>
Closure<R>* CNewClosure3(T* obj, R(T::*func)(const A1&, const A2&, const A3&)const, const A1& a1, const A2& a2, const A3& a3) {
    return new CMethodClosure_3_0<true, R, T, const A1&, const A2&, const A3&>(obj, func, a1, a2, a3);
}

int main(){
    ITask obj;
    string s1="abc";
    Closure<string>* closure1 = CNewClosure1(&obj,&ITask::WriteProfile1,s1);
    const string s2="xyz";
    const string&s3="333";
    Closure<string>* closure3 = CNewClosure3(&obj,&ITask::WriteProfile3,s1,s2,s3);
    return 0;
}
