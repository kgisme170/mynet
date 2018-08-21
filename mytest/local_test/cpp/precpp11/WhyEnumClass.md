为什么要有enum class的概念?

在C++11以前，定义和使用enum，已经像是定义一个class一样，enum可以有名称，也就是限定符：
```
namespace m {
class my {
public:
    enum A {
        u=1,
        v=2,
        w=3
    };
    static A f(A a) {
        return static_cast<A>(a + u);
    }
};
}
int main() {
    using namespace m;
    my::A r=my::f(my::u);
    return 0;
}
```

这个代码可以编译运行。眼尖的可以看到，在函数my::f里面，使用了一个static_cast。如果不使用，这里会有一个int到A的类型转换错误。
```
error: invalid conversion from ‘int’ to ‘m::my::A’
```

不是说好了enum默认当成int使用的吗？

其实这里的A有两个角色
1. 一个是像namespace/class一样，定义了一个type，而这个type本身只是存储为int，做运算时和int兼容，但是又不是int!
2. 但是另一方面，A又不是一个class/namespace同一级别的"名称空间"，因为我不能写
```
int i=my::A::u;
```

这句话会有编译错误
```
error: ‘class m::my::A’ is not a class or namespace
```

那么好了，enum A到底是个什么？我说它是一个int，编译器告诉我，做返回值时和int不兼容，没有隐式转换。好，那么我把它当成一个class/namespace来用，又告诉我不是一个class或者namespace。这个定义了某种type类型的关键字确实是类型系统里面的一个洞，这会直接导致很多相关的类型推导，模板等地方，不能很好的使用enum这个关键字定义的东西。

C++11的选择是：进一步确定这个enum是一个type，而不是一个int。enum class就是这个用途。注意，enum class可以指定存储的大小(例如int)，但是不能把这个存储的大小认为是和指定的存储‘类型’兼容，例如：
```
using namespace std;
enum class A:int {
    u=1,
    v=2
};
template<A value>
int next(){
    return value+1;
}
int main() {
    next<A::u>();
    return 0;
}
```

不能把":int"看成是从int继承。这里A是个更加strong typed的enumeration类型，必须使用static_cast转成int才能和int相加。也就是类型的限制更加严格了。
为什么要这样做？为了防止enum里面定义的符号，自动被注入到包含这个enum的class或者namespace内。这个自动注入会引起名称冲突，因为这种注入显然和符号生命的作用域概念是冲突的。C++11的选择是修复了这个bug，同时预留了语法上的向后兼容性，也就是向老版本兼容。(注：我们的中文词语有些语义混乱的地方，例如'向后看'表示'看以前'，'向前看'表示'看以后',希望有一天中文在技术上也能崛起和担当)
