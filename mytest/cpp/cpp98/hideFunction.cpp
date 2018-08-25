struct Base{
    virtual void f(){}
    virtual void f(int){}
};
struct Derived:Base
{
    using Base::f;
    void f(int){}
};
int main(){
    Derived obj;
    obj.f();
    return 0;
}