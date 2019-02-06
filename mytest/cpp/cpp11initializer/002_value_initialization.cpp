struct A{int i;};
struct B{int i;
    B():i(3){}
};
int main(){
    A* a1=new A;//a1->i是个随机值，未初始化
    A* a2=new A();//有了()，a2->i就是0，做了value-initialize
    int *pi=new int(7);//注意，不是pi指向的不是数组，而是一个单个的int，初始化为7
    int *qi=new int[8]();//申请8个int，全都初始化为0
    B* pb=new B[5]();//申请5个B类型对象，调用B::B()初始化

    A* p=new A[5]{{1},{2},{3}};//剩下的两个A初始化为0
    return 0;
}