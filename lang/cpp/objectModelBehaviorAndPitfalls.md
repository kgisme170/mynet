对象模型的陷阱

### <font color=#0099ff>virtual</font>: 什么时候实际起作用

<font color=#0099ff>virtual</font>关键字定义的函数,只要没有被内联消除(编译器能看到代码就可能做内联)，其行为取决于运行时的具体对象,而不是编译时的类型。但是一旦涉及到"参数传递"的语义，就会有很复杂度的行为。
```
#include<stdio.h>
struct B{
    B(){printf("构造\n");}
    virtual void f()const {printf("Base\n");}
};
struct D : B{
    void f()const {printf("Derived\n");}
};

void Keng1(B b){b.f();}
void Keng2(const B& b){b.f();}

int main(){
    D d;
    B b=d;
    B& rb=d;
    printf("Keng1--------------\n");
    Keng1(d);  //1.打印什么
    Keng1(b);  //2.打印什么
    Keng1(rb); //3.打印什么

    printf("Keng2--------------\n");
    Keng2(d);  //4.打印什么
    printf("还会有新对象吗?\n");
    Keng2(b);  //5.打印什么
    Keng2(rb); //6.打印什么
    return 0;
}
```
运行的结果是
```
构造
Keng1--------------
Base
Base
Base
Keng2--------------
Derived
还会有新对象吗?
Base
Derived
```

可以看到，我们从d对象直接构造一个b的对象，新的对象就是Base类型，而如果构造一个B类型的引用rb，那么这个引用当我们仍然按照引用传递的时候，它呈现的是多态行为。
1. Keng1这个函数接收值传递。尽管按值传递需要有拷贝构造语义，但是由于copy elision的缘故，并没有真的做无用的拷贝，因为Keng1里面没有修改对象b。完全按照参数类型(编译时是B类型)进行调用。从D对象构造B对象。
2. Keng2这个函数接收<font color=#0099ff>const</font>引用，因此当参数是引用类型B&的时候，会由编译器判断，为多态类型呈现指针语义(多态，运行时行为)，而当参数是值类型B的时候，仍然呈现编译时类型决议。

所以Keng2比Keng1先进的地方在于: 参数类型是引用，那么可以让实参的传递保持相应的多态语义。因此，我们不能简单的认为，一个类型有<font color=#0099ff>virtual</font>，它的对象就总是会呈现多态行为。这个和调用方也有关联。Keng2的实现才更加正确。

#### 为何有些事情不要做
另外我们也知道，不能在构造和析构的时候，在基类ctor里面调用纯虚函数: 因为构造基类的时候，<font color=#0099ff>this</font>指针还是通过vptr指向纯虚函数地址，下面这个程序就会输出"pure virtual function call"并core
```
#include<stdio.h>
struct B{
    virtual void pure() = 0;
    void h() {printf("Base\n");pure();}
    ~B(){h();}
};
struct D : B{
    void pure(){}
};
int main(){
    D d;
    return 0;
}
```

但是更隐蔽一点的例子，没有上述问题的代码: 对于数组而言，数组元素的遍历是编译器生成的指针移动的代码:
```
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
```
上面这个代码编译没有任何告警，运行时可能会崩溃(说好了析构函数是声明了<font color=#0099ff>virtual</font>就OK的呢)。因为pa实际是B类型的指针，B类型的大小是2*<font color=#0099ff>size_t</font>，而A类型的大小是<font color=#0099ff>size_t</font>
```
:size_t字节:size_t字节:size_t字节:size_t字节
<------B[0]----------><-------B[1]------->
<--A[0]---><---A[1]-->
```

<font color=#0099ff>delete</font>[] pa;的时候，<font color=#0099ff>new</font>出来的内存被当成A的元素----每次移动size_t大小，调用一次<font color=#0099ff>virtual</font> ~A()。而实际的结果是<font color=#0099ff>delete</font> A[1]的时候，实际上是B[0]的<font color=#0099ff>int</font> i被当成了A类型的<font color=#0099ff>this</font>指针，所以也就被用做了A::vptr指针来调用虚析构函数，当然崩溃了。

为什么编译不报错----因为说好了是运行时决议。那么100%保险的办法，用vector<share_ptr>去替换掉裸指针。OK，现在有了share_ptr就可以任性了吗?

#### 对象模型的缝隙
C++对象模型的能力之一，是在遇到<font color=#0099ff>exception</font>的时候，当前上下文的所有对象可以被unwind回收。尤其对于share_ptr而言，设计出来是保证里面管理的对象一定能被成功析构的。

但是这里有个缝，如果在类的ctor里面抛出异常，那么正在构造的这个对象本身，是不能被unwind的: 因为unwind要求是一个被完全构造出来的对象，才能能dtor去unwind，否则程序行为undefined，这是完整性原则。有一种误解是如果把对象放入share_ptr，那么ctor抛出异常的话share_ptr能否保证里面的元素被析构----其实不能，否则违反了完整性原则。
```
#include <iostream>
#include <memory>
#include <string>
using namespace std;
int i=0;
class G{
    G(const G& g){}
    char* m_str;
public:
    G(){
        ++i;
        m_str=new char[200];
        if(i>2)throw int(1);
        cout<<"default\n";
    }
    ~G(){
        --i;
        if(m_str)delete[]m_str;
        cout<<"destructor\n";
    }
};

int main(void){
    try{
        auto obj1=make_shared<G>();
        auto obj2=make_shared<G>();
        auto obj3=make_shared<G>();
        auto obj4=make_shared<G>();
    }catch(...){
        cout<<"i="<<i<<endl;
    }
    return 0;
}
```
上面这个程序,g++ -g编译运行，valgrind能检测到i=3的时候，抛出exception，没有构造完成的obj3和obj4的share_ptr::get()指向的对象泄露了: 虽然只能指针obj4本身没有泄露，被unwind了。(share_ptr是exception safe的，见C++11标准Section 20.7.2.2.1，但是，这个safety不包括它管理的对象，原因如前面的解释)

如果是windows平台使用VC的同学，上述内存泄漏也可以在VC的ide里面抓住，需要修改一下main函数
```
int main(void){
    _CrtSetDbgFlag(_CRTDBG_LEAK_CHECK_DF | _CrtSetDbgFlag(_CRTDBG_REPORT_FLAG));  
    _CrtDumpMemoryLeaks();
    try{
        auto obj1=make_shared<G>();
        auto obj2=make_shared<G>();
        auto obj3=make_shared<G>();
        auto obj4=make_shared<G>();
    }catch(...){
        cout<<"i="<<i<<endl;
    }
    return 0;
}
```
#### 另一个陷阱

除了ctor不要使用<font color=#0099ff>virtual</font>函数以外(不管是否是纯虚的)，多态函数还有一个陷阱:
```
#include <iostream>
#include <string>
using namespace std;
struct Base{
    virtual void f(const string& s="Base"){
        cout<<s<<endl;
    }
};
struct Derived:public Base{
    virtual void f(const string& s="Derived"){
        cout<<s<<endl;
    }
};
int main(int argc, char* argv[])
{
	Base* p = new Derived();
	p->f();
	delete p;
	return 0;
}
```
这里，虽然函数调用是运行时决议，但是默认参数的值是编译时决议的，程序输出"Base"，就好像多态没有起作用一样。

综上，我们不能简单的认为，加了<font color=#0099ff>virtual</font>关键字，相关的程序行为就在运行时决定了----决定因素还有编译期的判定，调用的时机和参数传递的规则等等。以上这些都是对象模型的一部分。Plus: C++11里面规定，dtor必须是<font color=#0099ff>noexcept</font>的，干脆用一个君子协定，规定编程者的价值观必须是不要从析构里面抛异常。

#### 指针调整
指针调整是编译器会自动做的事情之一，对于强类型语言而言，通过指针调整生成代码可以避免额外的对象拷贝或者运行时的内存分配。但是其语义是隐式的(implicit)，所以值得仔细的讨论和梳理。

首先要定义明确的是，我们说的pointer adjustment是在逻辑机器层面说的地址调整，不是C语言层面的指针: 一个C语言的变量在机器层面是一个地址，一个C语言的指针，保存的是机器层面的地址的地址。

那么对于更加复杂的继承关系而言，运行时的多态和闭包需要两个实现技术:
1. 指向成员的指针(functional,functor,closure)需要用到pointer adjustment，编译时确定指针对象的存储结构，以支持this指针的调整。
2. 运行时的函数调用地址，是用对象<font color=#0099ff>this</font>指针最前面的vptr指向vtable,这个在老书[深入理解C++对象模型]已经讲的不能更清楚了，不再赘述。

注意上面的(1)和(2)是并行不悖的。看下面一个程序:
```
#include<cstdio>
using namespace std;
struct base01{
    virtual void f(){printf("base01\n");}
    int i;
};
struct base02{
    virtual void f(){printf("base02\n");}
    int j;
};
struct derive:base01,base02{
};
int main(void){
    void (base01::*pf1)()=&base01::f;
    printf("%lu\n",sizeof(pf1));

    void (base02::*pf2)()=&base02::f;
    printf("%lu\n",sizeof(pf2));

    derive d;
    base02* pb=&d;
    (pb->*pf2)();
    return 0;
}
```
这个程序打印了两个pointer to member指针的大小，然后调用了pb->*pf2，实际指向了d对象。
```
16
16
base02
```
可能会感觉稍有点意外。因为通常一个普通指针的大小是<font color=#0099ff>size_t</font>，但是显然我们这里pointer to member的大小是2倍的<font color=#0099ff>size_t</font>。为何? 就是为了保证对于多态的情况, pointer to member可以正确工作。上面的例子中，derive继承自base01和base02，所以有两个vtable起始地址，换句话说，对于derive的base01部分，虚函数调用直接把this当作vptr，但是对于derive的base02部分，虚函数调用可能需要把this+size_t当作vptr(当然，编译器也可以实现为两个vtable和并，但是仍然需要在访问base02虚函数的时候，有偏移量的调整)。也就是说，pointer to member是一个结构体
```
this
adjustment
```
从对象的起始地址开始，加上一个偏移量，才是指向成员函数的地址。这个运行时的调整有赖于编译期生成一个结构体来保存真正需要的偏移量：
```
base02* pb=&d;
```
编译器知道base02是d的第二个部分，所以就为pb生成一个结构体，偏移了size_t大小。

如果没有这个结构体，pf2就是一个直接的虚函数调用: 把d对象的起始地址<font color=#0099ff>this</font>看作base02::vptr->vtable，但其实这个是指向base01::vptr->vtable的，那就会调用打印base01，不符合我们对于多态的预期了。

常见的functor/function/closure的实现，在lambda没有出现的时候，都是采用指向成员的指针实现的。我们的飞天共享库经常可以看到类似下面的的代码:
```
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
```

程序的输出是
```
3
12
12
12

```
这就是很好的例子，使用pointer adjustment保证行为正确的例子。

恒名
