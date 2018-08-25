一文阐明: C++模板元编程

如果一个信息是编译时可以确定的，就不要依赖运行时的行为。这样，可能的错误就能在编译时被检验出来。程序的正确性，性能都能有大幅提高。拥有这种能力的语言不多，C++借助<font color=#0099ff>template</font>可以做到这一点: C和fortran的效率，C#/Java式的设计模式。

1. 对于已知的递归条件，运行时的递归可以被编译时的递归替代
2. 对于已知量的计算过程，可以通过模板的类型推导得出常量。
3. 对于类型系统的分支和判断条件，运行时的<font color=#0099ff>if</font>/<font color=#0099ff>else</font>可以通过重载匹配/模板特化做到
4. 对于已知的循环，可以通过编译时的模板尾递归做到(C++11之后)
5. 实际的应用: 表达式模板和类型列表
后面分别举例。

### 从最简单的开始: 编译时的递归和计算
例如求n!，也就是1*2*...*n，可以通过编译时直接求解。10!=3628800，如下:
```
template<int i>
int Multiply(){return i*Multiply<i-1>();}
template<>
int Multiply<1>(){return 1;}

cout<<Multiply<10>()<<endl;
```
和运行时的递归不同，Multiply被编译器展开成为一个常数，没有任何运行时计算。同样的计算一个数组的大小<font color=#0099ff>sizeof</font>(buf)/<font color=#0099ff>sizeof</font>(buf[0])可以被下面的代码替代:
```
template<typename T,size_t n>
size_t countof(T (&buf)[n]){
    return n;
}
```
模板可以接受一个数组，并不发生decay。

### 类型系统的设计保证: 用重载匹配替代<font color=#0099ff>if</font>/<font color=#0099ff>else</font>或者<font color=#0099ff>switch</font>/<font color=#0099ff>case</font>

#### 应用1. 类型的检查
```
class Base{};
class Derived:public Base{};
class Type1{};
class Type2{};
class MyClass{
    Type member;
    typedef Type memberType;
};
```
上面的代码有一个类myClass，其中一个Type类型成员名叫member，我希望写一个UT，用来确保，memberType这个类型是Base或者从Base继承而来的类型(Derived等等)。如果是C#/Java，要做到这个UT，就只能用一个基类Base声明这个member，然后运行时创建(<font color=#0099ff>new</font>一个出来)，然后在UT代码里面去<font color=#0099ff>instance of</font>。C++也可以用Base*或者shared_ptr<Base>来声明member，在构造函数里面去<font color=#0099ff>new</font>出来，在UT里面去比较<font color=#0099ff>typeid</font>。这没有什么问题。但是:

(1) 毕竟如果为了这个UT，硬是把member从一个普通的成员，变成一个指针或者智能指针，不但破坏了原有的设计，而且能避免使用指针的时候，为什么不避免呢。
(2) 变成了指针，不但加入了运行时的代码，而且引入了各种出错的可能: C#/Java需要各种判断引用不为<font color=#0099ff>null</font>的代码，远不如操作C++普通对象的代码干净。
(3) 把静态类型约束作为"可编程"的一部分，主流编程语言里面只有C++能做到。可以像下面这样:

```
template <typename Base, typename Derived>
struct my_is_base_of{
    struct Yes{char _;};
    struct No{char _[2];};
    static Yes _test(const Base*);
    static No _test(void*);
    static const bool value=sizeof(_test((Derived*)0))==sizeof(Yes);
};

template <typename Base, typename Derived>
bool is_base_derived()
{
    return my_is_base_of<Base, Derived>::value;
}

#include<iostream>
using namespace std;
struct ExceptionBase{};
struct MyException : ExceptionBase {};
struct C {};

int main()
{
    cout << boolalpha;
    cout << "a2b: " << is_base_derived<ExceptionBase,MyException>() << '\n';
    cout << "a2b: " << is_base_derived<ExceptionBase,ExceptionBase>() << '\n';
    cout << "a2b: " << is_base_derived<MyException,ExceptionBase>() << '\n';
    cout << "c2b: " << is_base_derived<ExceptionBase,C>() << '\n';
    return 0;
}
```
上面的代码中，my_is_base_of是一个模板类，它定义了一个静态<font color=#0099ff>bool</font>类型常量value。这个value的值取决于两个模板参数类型之间的关系，如果Derived是Base或者Base的继承类，那么value就是<font color=#0099ff>true</font>，否则就是<font color=#0099ff>false</font>。怎么做到的?
(1) value的值来自于一个<font color=#0099ff>sizeof</font>的比较，也就是两个重载函数返回值类型的比较，编译器会判断Derived和Base之间是否有继承关系，如果有，那么Derived类型的指针就可以向上转型成Base类型的指针，否则只能匹配<font color=#0099ff>void</font>*。这就是在编译时用了重载解析替代了<font color=#0099ff>if</font>/<font color=#0099ff>else</font>。C#/Java做不到这一点。
(2) 当然让用户直接用my_is_base_of(T,U)::value丑陋了一点，于是有了一个模板函数is_base_derived来拿到这个返回值，这样用户就可以像调用普通函数一样拿到这个常量了。这个常量可以继续被用做重载匹配和类型推导。
(3) 上面这一段程序的输出是:
```
输出是
a2b: true
a2b: true
a2b: false
c2b: false
```

回到我们的需求来: 要保证memberType是Base或者Base的继承类型，那么只需要在合适的地方(代码当中，或者UT当中)
```
static_assert(is_base_derived<Base, myClass::memberType>()==true);
```
就可以了，只要编译通过，就说明没有问题，甚至不需要运行时的代码。对于C++编译器比较低的版本，不支持C++11的<font color=#0099ff>static_assert</font>，可以自己实现一个类似的功能，原理上和my_is_base_of类似，利用模板特化来做到编译时的条件分支，最简单的一个版本:
```
template<bool b>
void STATIC_ASSERT_TRUE();
template<>
void STATIC_ASSERT_TRUE<true>(){}
```

用的时候就可以这样:
```
    STATIC_ASSERT_TRUE<1==1>(); //链接通过
    STATIC_ASSERT_TRUE<1==0>(); //链接不通过
```
上面的原理很简单: STATIC_ASSERT_TRUE这个函数模板，本身没有实现代码；只有一个为<font color=#0099ff>true</font>做了特化的版本有实现，<font color=#0099ff>false</font>版本没有实现。所以在链接的阶段会报找不到`void STATIC_ASSERT_TRUE<false>()`的定义。

能不能更进一步，在编译时就让STATIC_ASSERT_TRUE抓到错误? 可以做到，要类似上面的模板类+模板函数，定义一个空的模板类，为<font color=#0099ff>true</font>的特化版本里面有一个成员，模板函数用来判断这个成员的大小: 对于<font color=#0099ff>false</font>版本，由于找不到这个成员的大小，于是编译会报错。尝试动手实现一个这样的版本:
```
template<bool b>
struct STATIC_ASSERT_TRUE{};
template<>
struct STATIC_ASSERT_TRUE<true>{static int i;};

template<bool b>
bool TEST_TRUE(){return sizeof(STATIC_ASSERT_TRUE<b>::i)==sizeof(int);}
```

调用的时候
```
    TEST_TRUE<1==1>();
    TEST_TRUE<1==0>();//编译错误: error: no member named 'i' in 'STATIC_ASSERT_TRUE<false>'
```
这样就达到了目标。

#### 应用2. 编译时的分发:
我们知道对于STL而言，容器，迭代器，分配器，算法，是分离的。算法只知道迭代器。那么如果不知道容器的类型，算法如何适配? 一个经典的问题是std::advance函数，这个函数用来将迭代器向前移动n个单元(n可以是负数)。那么问题来了，不同的容器，其内部迭代器实现并不相同，有的是可以随机访问的，有的不能随机访问，怎么办?

C#/Java的同学可能会想到，每个容器的迭代器要有一个标记，运行时让advance来判断这个标记，然后执行不同的操作。我们前面说了，在编译时我们是知道这个迭代器来自于哪个容器的，所以考虑用重载匹配来替代<font color=#0099ff>if</font>/<font color=#0099ff>else</font>。一个简单的实现如下:
```
#include<iostream>
using namespace std;
struct RandomAccessIterator{};
struct NormalIterator{};
struct Container1{//std::vector,deque,array等
    struct iterator{
        typedef RandomAccessIterator iterator_type;
        void operator+=(size_t len){}
    };
    iterator begin(){return iterator();}
};
struct Container2{//std::list,forward_list等
    struct iterator{
        typedef NormalIterator iterator_type;
        void operator++(){}
    };
    iterator begin(){return iterator();}
};
template<typename IteratorType>
IteratorType advance_impl(IteratorType it,size_t steps,RandomAccessIterator)
{
    it+=steps;
    return it;
}
template<typename IteratorType>
IteratorType advance_impl(IteratorType it,size_t steps,NormalIterator)
{
    for(size_t i=0;i<steps;++i)++it;
    return it;
}
template<typename IteratorType>
void advance(IteratorType it,size_t steps){
    advance_impl(it,steps,typename IteratorType::iterator_type());
}
int main(){
    Container1 c1;
    advance(c1.begin(),3);
    Container2 c2;
    advance(c2.begin(),3);
    return 0;
}
```

原理是: 每个容器的迭代器里面使用<font color=#0099ff>typedef</font>定义当前迭代器类型对应的Accessor类型。这个导入的类型信息，会被advance这个模板函数萃取，然后用于重载解析匹配到对应的的advance_impl函数。

### 对于已知的循环，可以通过编译时的模板尾递归做到
我们经常有这样的需求: 将几个变量传递给一个函数，进行累加。有几种不同的实现方式:
1. 把变量放到一个容器里面，实现一个函数来累加容器的元素。代价是需要构造一个容器，要么引入拷贝，要么使用指针或者引用代码声明周期管理的风险。C#的做法类似，有一个<font color=#0099ff>params</font>关键字作语法糖，把用户输入的若干个参数做成一个列表，传给函数。需要一个for循环。
2. 使用C风格的va_list。运行时的解析，有效率代价和结束条件问题，代码难看且很容易出错。
3. 用模板来做，类似bind函数的模板，从1个参数到9个参数都实现了。代价是一堆模板函数的实现，可扩展性全无。

问题来了: 能否根据用户写的代码，编译时就确定有多少个参数，并且根据参数的多少，直接算出来最后的结果，得到一个常量?

这就需要用到C++11引入的可变长参数模板。这个可变长是编译器可以计算的，我们可以通过类型系统进行计算和操作。
```
#include<iostream>
using namespace std;
template<typename T>
void printAll(T&& value){
    cout<<value<<',';
}
template<typename Head, typename...Tail>
void printAll(Head&& h, Tail&&...tail){
    cout<<h<<',';
    printAll(forward<Tail>(tail)...);
}
template<typename T>
T sumAll(T&& value){
    return value;
}
template<typename Head, typename...Tail>
Head sumAll(Head&& h, Tail&&...tail){
    return h+sumAll(forward<Tail>(tail)...);
}
int main(){
    printAll(1,2,"xyz",3);
    cout<<'\n'<<sumAll(2L,'a',3)<<endl;
    return 0;
}
```
注意，模板参数传递使用Universal reference(&&)，递归的时候要保持参数类型，所以要加上forward<Tail>(tail)...

对于上面这个sum函数，还有一种解法，用迭代来代替尾递归，让编译器自动推导，这样，我们就不需要一个初始条件的sumAll函数了:
```
template<typename Head, typename...Tail>
Head sum(Head&&h, Tail&&... tail){
    Head r=h;
    (int[]){(r+=tail,0)...};
    return r;
}
```
这里使用了一个可变长度的表达式模板，也就是编译器为tail这个尾巴展开了一个数组:
```
(int[]){(r+=tail,0)...};
```
这个数组每个元素都是tail，展开的过程中对(r+=tail,0)这个表达式求值，也就是r的计算在编译器展开参数列表的过程中完成了，连递归都没有了，完全只是一个迭代。
```
cout<<'\n'<<sum(2,3,4)<<endl;
```
输出`9`

使用上面的sumAll和sum函数模板作为参考，我们可以得到计算参数个数的模板countof(C++11里面的sizeof模板)。有兴趣的同学自己看下怎么改代码:)

### 应用举例

#### 表达式模板
我们希望有C/fortran般的执行效率，有C++/Java般的OO设计，又有强类型的编译时类型匹配和检查。一个经典的问题是矩阵和向量运算，典型代码如下:
```
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
```
可以看到，我声明了几个数组元素a1,a1,a2,a4，把他们加起来得到a5。为了达到OO的设计效果，我们设计了<font color=#0099ff>operator</font>+，也就是每两个Array对象都要调用一次这个函数。这样做的好处是代码清晰可扩展性好，代价是，有几个<font color=#0099ff>operator</font>+就有几个循环。如果是C/Fortran代码，做法就是写一个for循环，把4个Array的对象元素都加起来，赋给a5，减少了大量的循环和加法，以及内存的读写操作，效率提高了很多。但是这样的代码可扩展性不好，破坏了开闭原则，难以维护。

能否二者兼得? 那就是要用到C++的模板元编程。上面我们用的技术，用来解决这个问题。
(1) 仍然保持OO设计。
(2) 为了减少多次循环和计算，我们的<font color=#0099ff>operator</font>+的实现，不再是计算结果，而是返回一个中间类型(表达式对象)。
(3) 这个表达式对象可以继续和其他对象做预算，生成新的表达式对象
(4) 最后当编译器看到"="的时候，表达式对象才出发真正的求值(evaluation)操作，调用一个循环，把结算算出来。

上面的4个步骤，接近于人类的思考方式，不但优雅而且高效，最关键的一点是，为了减少中间结果的运算和求值，我们需要引入"懒惰计算"的思想，不到最后一步，不求值，只是得到一个可以用来求值的表达式。

在动手写代码之前，有几个需要注意:
(1) 现在不能把<font color=#0099ff>operator</font>+作为ArrayExp的一个成员函数，因为+的操作数可能是ArrayExp，也可能是我们要使用的中间对象。所以<font color=#0099ff>operator</font>+现在是一个模板函数，返回一个表达式模板expr_add实例。
(2) 这个expr_add类型，定义了一个求值函数<font color=#0099ff>operator</font>[]
(3) expr_add可以和ArrayExp继续做<font color=#0099ff>operator</font>+运算。
(4) 最终，ArrayExp构造函数，用来从expr_addr对象里面，触发所有的计算，拿到最终的结果。

```
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
```

上面的这个实现，充分体现了FP编程的lazy evaluation和continuation的思想，通过将中间结果存储为可计算的chunk，用空间换了时间，最后只需要一把求值就得到了最后结果。expr_add必须有一个<font color=#0099ff>operator</font>[]来表明自己是可计算的一个chunk。

#### 类型列表[Modern C++ design的核心问题之一]
我们希望从一个类型列表里面取出一个，用来创建变量:
```
int main(){
    typeList<2,int,short,char*,int*>::type x="ehllo";
    return 0;
}
```
也就是有一个下标从0开始的参数列表，拿出第2个类型来。A.A.大神Loki库用了非常多的笔墨来实现它，但是现在有了C++11的可变长参数模板，可以很容易的实现了:
(1) 如何计算next(递归)
(2) 终止条件
所以一共需要2个定义。
```
template<size_t index, typename Head, typename...Tail>
struct typeList{//特化1
    using type=typename typeList<index-1, Tail...>::type;
};
template<typename Head, typename... Tail>
struct typeList<0,Head,Tail...>{//特化，终止条件
    using type=Head;
};
```
这样就构造出来了typeList工具。

恒名