## 初始化语义和参数列表的演进

C++11/14/17的初始化语义的增强和模板的语义的增强都可以看作是对类型系统的增强。为什么要增强类型系统? 写出更加正确的程序，更容易验证程序的正确性。

1. 能在编译时创建的信息，就不需要在运行时创建
2. 能在编译时建立的约束，就不需要在运行时检查
3. 能用类型表达的信息，就不需要用对象和变量
4. 能用固定表达式表示的信息，就不需要设计动态的存储和调用

我们按照以下的顺序讨论
(一)3种基本的初始化语义及其演进
(二)初始化语义和模板，auto/decltype
(三)变长参数模板
(四)应用

### <br>(一)3种基本的初始化语义及其演进

#### 1. Zero initialization
回忆一下: C++/C#/Java有多少问题和bug是由使用未初始化的变量引起。C++中的圆括号对不仅表示class/struct对象的构造，而且可以将()用来初始化单个值，可以表示无参或者默认的初始化(赋0)
```
int i(5);
short s=short();//0
```
同样，对于数组而言，不需要ZeroMemory或者memset，只要按照规则书写就可以:
```
int buf[10]={1};//第一个元素初始化为1，剩下9个元素全都初始化为0
```
那么对于很多C风格结构体的初始化，可以很方便的像下面这样写:
```
struct A{
    size_t size;
    int i;
    ...
};
A obj={sizeof(A);}//这样，第一个size元素就被正确的初始化了，其余成员都初始化为0
```

对于静态成员，非局部成员，编译器能保证在分配后初始化为0
```
int global_i;
int main(){
    static int i;
    cout<<global_i<<i<<endl;//打印00
}
```
对于字符数组而言，编译器自动分配一个0到末尾:
```
char buf[10]="";//以\0结尾的字符串
```

#### 2. default initialization
default-initialize 始于C++98: 对于没有声明用户自定义构造函数的类型，或者声明对象数组:采用默认构造函数(default-constructor)，其他后面没有跟任何表达式或者等号和括号的对象声明，编译器什么也不做。(所以会有未初始化的变量或数组!)
```
struct A{};
A a1;
A buf[3];
```
http://en.cppreference.com/w/cpp/language/default_initialization

#### 3. value-initialization: 
C++03开始增强，包含有参数的情况和zero-initialization。
1. 如果class有构造函数，就调default ctor
2. 如果class没有构造函数，而且也不是union-type的class，那么zero-initialize
3. 如果是数组类型，每个元素都做value-inialization
4. 否则，没有初始化
```
struct A{A(int){}};
A a1(1);
int i(2);
```

注意value-inialization的一个额外的规则是为new这个关键字建立的: 如果声明的时候，对象的构造参数是一个空的()，那么()要做value-initialize:
```
struct A{int i;};
struct B{int i;
    B():i(3){}};

A* a1=new A;//a1->i是个随机值，未初始化
A* a2=new A();//有了()，a2->i就是0，做了value-initialize
int *pi=new i(7);//注意，不是pi指向的不是数组，而是一个单个的int，初始化为7
int *qi=new i[8]();//申请8个int，全都初始化为0
B* pb=new B[5]();//申请5个B类型对象，调用B::B()初始化
```

#### 统一的初始化列表
C风格的<font color=#0099ff>struct</font>和数组可以用{}来初始化，而C++的规则使得()可以作为某种construct语义来初始化变量。于是C++11开始引入了统一的初始化语义: {}。这个语义不但使得书写可以变得简介，而且避免了老版本C++的一些问题:
```
struct A{};
int main(){
    A a1();//a1是一个函数，most vexing parse导致的
    A a2{};//a2是一个对象，初始化。
}
```
现在可以直接用{}给单个变量初始化，就像()那样。现在，{}引出了更大范围的初始化规则(内部会用到前面的3种初始化)。
1. Aggregation(聚合) initialization
所谓聚合就是数组或者是个类似POD的类型，不含有C++特有的2进制结构(ctor, static, virtual等)。Aggregation initialization是list initialization的一种形式，作用与聚合。
http://en.cppreference.com/w/cpp/language/aggregate_initialization

#### Aggregation initialization 一般规则:
像给数组元素赋值一样给C风格的<font color=#0099ff>struct</font>赋值，如果{}内的值不够，那么剩余的赋0: zero-initialize
(1)可以嵌套
(2)应用于类，类不能有用户自定义的ctor，但可以有default ctor和dtor
```
struct A{
    int i;
    struct B{
        int i;
        int j;
    };
}a={2,{3,4}};
```
是不是像Python/Javascript对象声明一样爽? 当然这个必须只针对于POD类型，如果A里面有`A(){}`或者`A()=default`就不行了，不能有用户自定义的special member function。

#### Aggregation initialization 对于多维数组的规则
如果用一维的{}值列表给多维数组赋值，那么优先考虑最右边的下标，依此类推。(http://timepp.github.io/doc/cpp14/dcl.init.aggr.html)
```
int main(){
    int buf[2][2]={1,2,3,4};
}
```
那么buf[0][0],buf[0][1],buf[1][0],buf[1][1]分别是1,2,3,4，完全按照拍平的规则来。

#### 列表元素的初始化

现在可以像python/javascript那样初始化一个容器及其元素了(http://timepp.github.io/doc/cpp14/dcl.init.list.html)
```
vector<string> vs{"hello","world"};
map<string, int> msi{{"hello",1},{"world",2}};
```

主要注意的是，前面出现的brace-initialize-list本身不是表达式，也没有类型，不能直接用于类型推导的场景(例如C++11的模板实参，lambda返回值不能直接用brace-initialization-list)。

#### 更多的细节讨论:
List-initialization又可以根据发生的场景继续分类
(A)Direct initialization(变量名后直接跟{}或者())场景下的称为direct-list-initialization(http://en.cppreference.com/w/cpp/language/direct_initialization)
1. 变量赋值(value-initialize)
2. 拷贝构造
<b>3. 普通构造
4. <font color=#0099ff>按值static_cast</font><T>
5. <font color=#0099ff>new</font>
6. 成员的初始化列表
7. 闭包的参数抓取 by copy (C++11的lambda表达式)</b>
上面粗体字的5种没有copy initialization的对应。

(B)Copy initialization场景下的称为copy-list-initialization(有=号赋值的语句)
1. 拷贝构造
2. 变量赋值(value-initialize)
<b>3. 返回值 by value
4. 函数传参by value
5. <font color=#0099ff>throw</font>/<font color=#0099ff>catch</font>的时候，未指定&符号，by value</b>
形如T array[N]={other}这样的表达式，作为aggregation initialization的一部分，以拷贝的方式创建数组的对象。

粗体字的3种没有direct initialization的对应。http://en.cppreference.com/w/cpp/language/copy_initialization
看个例子:
```
#include<iostream>
using namespace std;
struct A{
    A(){cout<<"A ctor\n";}
    A(const A&){cout<<"A copy ctor\n";}
    A& operator=(const A&){
        cout<<"A operator=\n";
        return *this;
    }
    A(A&&){cout<<"A move ctor\n";}
    A& operator=(A&&){
        cout<<"A move operator=\n";
        return *this;
    }
};
struct B{
    A a;
    int i;
};
int main()
{
    cout<<"direct initialization\n";
    B b1{A(),1};//require A(&&)
    cout<<"=============\n";
    B b2={A(),2};//require A(&&)
    cout<<"copy initialization\n";
    A a;
    B b3={a,3};//require A(const A&)
    return 0;
}
```

#### {}初始化和()不同的地方
对于指针数组的初始化(后向兼容问题)，前述的()是清0(value/zero-initialization)，而{}是逐个初始化  (value-initialization)
```
struct A{int i;};
A* p=new A[5]{{1},{2},{3}};//剩下的两个A初始化为0
```

#### intializer_list的类型要求: 必须是同种类型的元素:
braced-init-list表达式赋给变量, 如果依赖auto来推导类型，那么auto可能把它看成一个std::initializer_list, 前提是每个元素都是相同的类型。
```
struct A{};
struct B:A{};
auto x1={A(),A()};//OK
auto x2={A(),B()};//语法错误
```

#### 不能将inialization_list直接用作模板实参
```
template<class T>
void f(T t){}
int main(){
    f({1,2,3});//语法错误
    return 0;
}
```
因为{1,2,3}本身没有一个赋值语句来推导出类型，因此是一个non-deduced-type。这种type不能再次传递给模板(除了<font color=#0099ff>using</font>对<font color=#0099ff>typedef</font>的增强以外)，模板要求，模板函数"形参"的表达式对应的"实参"表达式类型是已知的，当成一个<font color=#0099ff>template class</font>，然后才能推导出表达式中的类型参数。解决的办法有两种:
```
方法1
template<class T>
void f(std::initializer_list<T>&&t){}//显示指定形参是initializer_list<T>这个

方法2
int main(){
    auto x={1,2,3};//或者用auto得到initializer_list类型。
    f(x);
    return 0;
}
```
为什么有这个规则? 因为std::initializer_list实现为对一个匿名数组的引用(而不是结构体)，那么返回一个std::initializer_list本身是相当于返回一个临时的数组(临时变量)，会导致运行时问题(随机值)
```
initializer_list<int> f(){return {1,2,3};}
for(auto& e in f()){
    cout<<e<<endl;//打印的可能是随机值
}
```
C++的语言标准解释道，(section 8.5.4)这个临时数组的声明周期等同于initializer_list对象本身的声明周期，用的时候要小心。

### (二)初始化语义和模板，auto/decltype的引申功能
我们知道重载解析是不能根据函数的返回值来推导类型匹配的，而<font color=#0099ff>auto</font>可以帮助我们推导返回值(C++11需要结合<font color=#0099ff>decltype</font>, C++14可以不用)，所以，当返回值是braced-init-list的时候，不能借助<font color=#0099ff>auto</font>来推导
```
template<class T>
auto f(T t1,T t2){
    return t1+t2;
}
```

需要注意<font color=#0099ff>auto</font>声明并初始化一个变量的时候，有陷阱:auto关键字萃取右值类型的时候，不萃取&，由于<font color=#0099ff>auto</font>不会包含引用符号，因此C++14引入了<font color=#0099ff>decltype(auto)</font>操作。另一个陷阱是在range based <font color=#0099ff>for</font>里面需要使用<font color=#0099ff>auto&</font>，否则会有对象拷贝。

#### 优先级?
既然有(),有{},有std::initializer_list，那么到底哪个先起作用? 如果存在构造函数接受初始化列表，那么初始化列表拥有最高的重载匹配的优先权。哪怕initializer_list的实例化类型不是最佳匹配。
```
struct A{
    A(int i){cout<<"A int\n";}
    A(const initializer_list<double> dlist){cout<<"A init\n";}
};
A a(1);//打印A init
```
由于initializer_list被优先匹配，而initializer_list不允许narrowing conversion(例如double到int的转换)，可能导致编译失败。把上面的例子改成:
```
struct A{
    A(double i){cout<<"A double\n";}
    A(const initializer_list<int> dlist){cout<<"A init\n";}
};
A a(1.0f);//编译告警或者失败，取决于编译器和编译选项
```

#### 空的初始化列表
匹配默认构造函数。含有一个空初始化列表的初始化列表，才匹配std::initializer_list，继续修改上面的例子:
```
struct A{
    A(int i){cout<<"A int\n";}
    A(const initializer_list<int> dlist){cout<<"A init\n";}
};
A a{};//打印A int
A a{{}};//打印A init
```

### (三)变长参数模板
网上可以找到很多教程，不再敖述。其具有自动的类型推导功能，能借助类型系统建立和推断规则。几个主要的应用场景有:
1. 各类泛型定义: <font color=#0099ff>typename</font>, <font color=#0099ff>typedef</font>, <font color=#0099ff>using</font>
2. 模板: 用类型推导表达约束(constraints)
3. 策略: 编译时依赖注入(traits)
4. 元编程和DSL
变长参数模板帮助我们在编译时确定程序的行为，避免分支和循环
其实C语言已经有了运行时的支持变长参数模板<font color=#0099ff>va_list</font>，但是语法丑陋，没有类型安全和数组越界保护，且不能用于模板。其他语言例如C#有<font color=#0099ff>params</font>关键字，类似C语言的va_list，需要额外构造一个数组，开销很大。

那么既然是变长参数模板: 需要注意的是对于调用者而言，编程时可以用任意多个参数，这个参数个数要在编译时确认。那么就不需要依赖运行时的行为，而在编译时就能展开参数列表，确定类型，检查错误，甚至编译优化。所以C++11根据这个需求引入了variadic template技术。用类型系统的推到规则来确认参数个数和类型。使用变长参数模板的两种方式: 编译器用模式匹配展开省略号。
<br>尾递归的版本:
```
#include<iostream>
using namespace std;
template<typename T>
void printLine(T&& value)
{
    cout << value << endl;
}

template<typename First, typename... Rest>
void printLine(First&& first, Rest&&... rest)
{
    cout << first << ",";
    printLine(forward<Rest>(rest)...);
}

int main()
{
    double d = 2.3;
    printLine(78,"hi",d);
    printLine("hi",d,'a');
    printLine(d,'a');
    printLine('a');
    return 0;
}
```
迭代的版本
```
template<class Head,class ...Tail>
Head sum(Head h,Tail&&...value){
    Head sum=h;
    (int[]){(sum+=value,0),...};
    return sum;
}
int main(){
    return sum(1,2,3.0);
}
```

#### (四)应用
变长参数模板可以帮我们计算变长参数个数等:
```
template<class T>
size_t f(T&&...elem){
    return sizeof...(elem);//C++11的sizeof
}
```
当然，没有sizeof的增强，我们也可以自己写一个:
```
template<class T>
size_t countof(T&&){return 1;}
template<class Head,class... Tail>
size_t countof(Head&& h,Tail&&... tail){
    return 1+countof(forward<Tail>(tail)...);
}
int main(){
    return countof(1,2,1,2,1);//5
}
```

那么它有什么应用呢? 很多运行时库和编码库，类型之间的”映射关系”是已知的，不需要在运行时创建映射。运行时根据数据确定调用哪一种映射关系。映射关系是静态的。
有很多种方式可以创建基于模板的类型映射，其中一种是类似前面的尾递归方法，创建type list，便于类型查找。Type list方法可以追溯到[Modern C++ design]，目标是能写出下面这样的客户端代码:
```
typeList<2,int,short,char*,int*>::type x="hello";
```
也就是从typeList里面取出第二个类型"<font color=#0099ff>char</font>*"，注意第0个类型是<font color=#0099ff>int</font>，第一个类型是<font color=#0099ff>short</font>。那么在C++03里面构造typeList是个非常繁琐的过程:因为要输出的是类型而不是值，typeAt必须是个模板类(而不是模板函数)，里面包含了类型的推导和定义。

递归定义typeList，需要3个递归条件:
1. 初始条件(空定义)
2. 如何求解下一个(编译时递归)
3. 中止条件(模板特化)
因此需要有3个typeList的模板定义。有了可变长参数模板，代码简洁多了:
```
#include<iostream>
using namespace std;

template<size_t index, class ... Tail>
struct typeList;

template<size_t index, class Head, class ... Tail>//long match
struct typeList<index, Head, Tail...>
{
    using type = typename typeList<index-1, Tail...>::type;
};

template<class Head, class ... Tail> // specialization of long match
struct typeList<0, Head, Tail...>
{
    using type = Head;
};

int main()
{
    typeList<2, int, short, char*, int*>::type x="hello";
    cout<<x<<endl;
    return 0;
}
```
这个可比loki库简单多了。

恒名
