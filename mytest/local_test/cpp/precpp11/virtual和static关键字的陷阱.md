virtual和static关键字的陷阱

### 多继承带来的问题
C++允许多继承: 因此有了虚拟继承的能力和相关的问题。为了绕开这些问题，Java/C#的选择就是干脆去掉了这种能力，有点因噎废食的味道。我们在开发过程中确实遇到过一个类似的问题，菱形继承是一种常见的类型设计方式，我们希望一个class从两个实现类继承，同时基类的成员保持只有一份。问题抽象如下。

```
#include<iostream>
using namespace std;
struct S1{
    int i;
    S1():i(2){}
    S1(int _i):i(_i){cout<<"S1:"<<i<<"\n";}
};
struct S2:virtual S1{
    S2(){}
    S2(int _i):S1(_i){cout<<"S2:"<<i<<"\n";}
};
struct S3:virtual S1{
    S3(){}
    S3(int _i):S1(_i){cout<<"S3:"<<i<<"\n";}
};
struct S4:S2,S3{
    S4(){}
    S4(int _i):S3(_i),S2(_i){cout<<"S4:"<<i<<"\n";}
};
int main(){
    S4 obj(3);
    return 0;
}
```

这个程序输出什么?
```
S2:2
S3:2
S4:2
```
也就是说，S4的构造参数"3"似乎根本没有起作用，而且，S1(int)没有被调用，而是调用了S1()!

解释: 由于菱形继承的缘故，为了保持S4的实例当中只有一份S1(并且只初始化一次)，那么我们看到的是
(1) S4的初始化列表，这个初始化列表里面显示的初始化了S2和S3的部分(虽然S3在前面)，但是并没有指定S1的部分要如何初始化
(2) 实际上因为要保证S1只被初始化一次，那么S4的初始化过程中，相当于编译器为S2(int)生成了两份代码，一份代码是S2实例化的时候，调用了S2(int _i):S1(int)，另一份代码是给S4生成的，也就是S4(int)调用的S2(int)是不包括S1(int): 如果不这样做的话，S4就会通过S2和S3对S1做了两次初始化。
(3) 所以，S4的ctor对S1的初始化，必须显示指定，否则，只在最开始的阶段调用了一个默认的S1()

上面3个规则，看起来复杂，但都是为了保证"在S4当中,S1只被初始化一次"，比较复杂的地方就是virtual继承来的类型，编译器会为其子孙类型再生成一份ctor代码，而这份ctor代码是不包含virtual基类初始化的。

上面这份代码:
(1) 如果注释掉S1(){}那么会有编译错误
(2) 如果把S4的ctor代码改成
`S4(int _i):S3(_i),S2(_i),S1(_i){cout<<"S4:"<<i<<"\n";}`

那么打印输出就变成了我们所期待的:
```
S1:3
S2:3
S3:3
S4:3
```
也可以参考网上的解释: https://stackoverflow.com/questions/2126522/c-virtual-inheritance

### static关键字带来的链接问题
C++语言只规定了，在一个编译单元里面(cpp及其头文件/宏的展开)，static意味着: 一个定义的作用域不超过本编译单元。那么问题来了: 如果多个编译单元里面有同名的static全局变量，那么会有什么行为呢?

C++语言没有规定这件事情，所以从语言的角度上说，是未知的，因为目前C++标准还没有引入"模组"的概念。那么其行为就取决于链接过程了。又由于C++用到的链接器是操作系统的链接器，而链接器是独立于编程语言的，它在史前时代就有了。所以还是要看这个链接器的行为。

一个实验: 定义一个头文件，里面有一个struct M的定义，
```
#pragma once
#include<iostream>
using namespace std;
struct M{
    int i;
    M(int _i):i(_i){cout<<"M构造,i="<<i<<endl;}
    ~M(){cout<<"M析构,this指针="<<this<<",i="<<i<<endl;}
};

void f1();
void f2();
```
有两个.cpp文件里面各自声明了一个M obj1对象。为了让编译单元不被链接器优化和裁剪调，我们在两个cpp文件里面又各自声明了了一个函数，让这个函数被main函数调用。
代码如下:
```
cat doubledefine01.cpp
#include"doubledefine.h"
static M obj1(20);

void f1(){}

cat doubledefine02.cpp
#include"doubledefine.h"
static M obj1(30);

void f2(){}

```

主函数定义如下:
```
#include"doubledefine.h"
int main(){
    f1();
    f2();
    return 0;
}
```

以不同的方式编译链接这几个文件。首先尝试 (1) 完全用.cpp代码编译在一起 (2) 两个double define的cpp文件编译成.o 再和main一起编译 (3) 两个.o文件打包成一个.a文件，再和main一起编译:

```
g++ -c doubledefine01.cpp -fPIC
g++ -c doubledefine02.cpp -fPIC

g++ doubledefinemain.cpp doubledefine01.cpp doubledefine02.cpp -o doubledefineCpp
g++ doubledefinemain.cpp doubledefine01.o doubledefine02.o -o doubledefineObj

ar cr libdoubledefineArchive.a doubledefine01.o doubledefine02.o
g++ doubledefinemain.cpp -L. -ldoubledefineArchive
```
上面这3中链接的方式，运行都得到以下的结果:
```
M构造,i=20
M构造,i=30
M析构,this指针=0x1086110e4,i=30
M析构,this指针=0x1086110e0,i=20
```
也就是说，同名的变量，各自有自己的存储和分配，不会有内存问题

如果改成动态库? (1) 两个.o分别生成.so (2) 两个.o打包成一个.so，然后再和main一起编译
```
g++ -o libdoubledefine01.so --shared -rdynamic doubledefine01.o
g++ -o libdoubledefine02.so --shared -rdynamic doubledefine02.o
g++ -o libdoubledefineboth.so --shared -rdynamic doubledefine01.o doubledefine02.o
g++ doubledefinemain.cpp -L. -ldoubledefine01 -ldoubledefine02 -o doubledefineSo
g++ doubledefinemain.cpp -L. -ldoubledefineboth -o doubledefineBoth
```
运行doubledefineBoth:
```
M构造,i=20
M构造,i=30
M析构,this指针=0x1097270e4,i=30
M析构,this指针=0x1097270e0,i=20
```
也没有问题，每个.o在.so里面处于不同的位置，没有内存问题。同一个符号表，两个对象对应不同的地址。

运行doubledefinSo:
```
M构造,i=20
M构造,i=30
M析构,this指针=0x10de560d8,i=30
M析构,this指针=0x10de500d8,i=20
```
也没有什么问题。好，接下来，把两个doubledefine文件当中的f1,f2函数去掉，看看。一样的效果。那么如果两个obj1的初始化参数都一样呢? 例如都是M obj1(20); 编译运行发现，也都没有问题，各自有一个obj1。也就是说，对于C风格的普通静态变量，不同编译单元当中可以有重名的，链接器能正确处理。

接下来，让一个struct N里面拥有一个M的静态成员，在doubledefine两个文件里面去初始化它。修改头文件，加上一段:
```
struct M{
    int i;
    M(int _i):i(_i){cout<<"M构造,i="<<*(int*)this<<endl;}
    ~M(){cout<<"M析构,this指针="<<this<<",i="<<*(int*)this<<endl;}
};

struct N{
    static M objn;
};
```
然后在doubledefine的两个cpp文件都加上这样的代码:
```
cat doubledefine01.cpp
#include"doubledefine.h"
void f1(){}
M N::objn(20);

cat doubledefine02.cpp
#include"doubledefine.h"
void f2(){}
M N::objn(30);
```
现在，非.so版本的编译不过去，因为链接器会报告双重定义的错误: 一个class当中的static变量当然只能有一份定义(语义规定如此)。但是对于link两个.so版本，链接器并不会报错: 因为第二个.so当中的static M N::objn定义可以在第一个.so当中找到，没有唯一定义的冲突和限制----换句话说，只要能找到一个定义就可以了。但是问题来了，全局的变量都是要析构的，在各自的编译单元里面都会插入退出的代码，这个static类成员最终会被析构两次: 在同一个地址上!
```
M构造,i=20
M构造,i=20
M析构,this指针=0x7f2ecb9baf1c,i=20
M析构,this指针=0x7f2ecb9baf1c,i=20
```

刚才的编译命令是这样的:
```
g++ -c doubledefine01.cpp -fPIC
g++ -c doubledefine02.cpp -fPIC

g++ -o libdoubledefine01.so --shared -rdynamic doubledefine01.o
g++ -o libdoubledefine02.so --shared -rdynamic doubledefine02.o
g++ doubledefinemain.cpp -L. -ldoubledefine01 -ldoubledefine02 -o doubledefineSo
```

我修改一个下最后一个命令的顺序，先链接doubledefine02，效果如下:
```
$./doubledefineSo
M构造,i=20
M构造,i=30
M析构,this指针=0x7f1e7bcf1f1c,i=30
M析构,this指针=0x7f1e7bcf1f1c,i=30
```

这次发现问题了，也就是说，两个obj1对象被指向同一个地址。怎么解释: 链接器在读取多个不同.so文件的时候，两个不同的符号表，同一个地址，不会再次分配不同的地址。
结论是: 不要使用超出编程语言语义范畴的运行时特性，否则行为未知。因为下一个版本的链接器可以把行为改掉。上面的代码在mac上做测试，是没有问题的: clang的链接器，把不同.so的同名变量放到了不同的地址，不会有double delete的问题。

那么既然在linux上面出问题的是类的静态成员，而不是普通的静态成员，那么namespace的情况如何? 实验告诉我们namespace当中定义的静态成员，在每个包含它的cpp里面都会有一份:
```
namespace N{
    static M objn(20);
};
```
doubledefine的cpp里面不能再定义它了，因为已经定义过了，运行的效果是:
```
M构造,i=20
M构造,i=20
M构造,i=20
M析构,this指针=0x6013b0,i=20
M析构,this指针=0x7f1d3559ded8,i=20
M析构,this指针=0x7f1d3539ced8,i=20
```
各不相同。

### 小结:
链接器并没有做错什么，是我们不应该使用同名的static变量。如果这个析构函数有内存释放，那么就会有double deletion错误。除非这个obj1对象是POD才安全----但是即使如此，结果也可能不是我们想要的。
