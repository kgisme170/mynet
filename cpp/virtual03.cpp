struct A{
    virtual ~A(){};
};
struct B: public A{
    int i;
    B():i(0){}
};
int main(void){
    A* pa=new B[2];
    delete[] pa;
    return 0;
}
