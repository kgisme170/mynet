C风格Pthread库和C++代码的兼容性陷阱

Pthread库是为C语言设计的: 它没有考虑C++语言的运行时(c++ runtime)的特性，例如栈对象的回滚(自动析构，异常回滚等等)。Pthread在C++程序中的的适用性问题这个问题并不是C++语言要考虑的问题: C++语言选择从C++11开始引入符合语言标准的C++线程库作为新的解决方案(不依赖pthread的实现)，避免pthread本身的设计带来的一些问题，同时标准库表明了规定了哪些事情不要做。

[问题一]: 在一个C++程序里面pthread_create创建的线程，如果运行过程中退出了，那么栈上的对象(拥有非trivial destructor的实例)是否要析构?

    这不是C++设计者语言要回答的问题，因为Pthread并不是C++的一部分。Pthread也不是C语言标准库的一部分，C++没有兼容的必须。Posix thread库要完全兼容C，而不是反过来。语言的实现者可以为常用系统库的适用性提供一些实现上的选择，但是这些选择是没有统一规范的，任何实现都是可能的。例如有一个C程序如下:(testPthreadCancel.c)

```
#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
void* tf(void*arg){
    int oldstate;
    int i=0;
    pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
    while(1){
        printf("sleep1\n");
        sleep(1);
        ++i;
        if(i==3){
            pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
        }
    }
    return NULL;
}
int main(){
    pthread_t tid;
    pthread_create(&tid,NULL,tf,NULL);
    sleep(2);
    pthread_cancel(tid);
    printf("cancel thread\n");
    pthread_join(tid,NULL);
    return 0;
}
```

在5u的开发机上用GCC4.1.2编译运行这个程序，会得到如下输出:
```
sleep1
sleep1
cancel thread
sleep1
sleep1
```

逻辑上是OK的，如果把后缀名改成.cpp，编译运行它，也没有问题。但是如果把C运行时库的print换成C++的cout:

```
#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
void* tf(void*arg){
    int oldstate;
    int i=0;
    pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
    while(1){
        printf("sleep1\n");
        sleep(1);
        ++i;
        if(i==3){
            pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
        }
    }
    return NULL;
}
int main(){
    pthread_t tid;
    pthread_create(&tid,NULL,tf,NULL);
    sleep(2);
    pthread_cancel(tid);
    printf("cancel thread\n");
    pthread_join(tid,NULL);
    return 0;
}
```

运行的结果会变成:
```
sleep1
sleep1
cancel thread
sleep1
FATAL: exception not rethrown
sleep1
Aborted
```

注意，同一台机器上，高版本的gcc4.9.2编译运行的结果还稍有不同:
```
sleep1
sleep1
cancel thread
sleep1
sleep1
Aborted
```
只有abort信息，没有FATAL: exception not rethrown。稍后我们会对比其他的平台和编译器。因为函数结束要退栈，那么含有C++对象的函数，不但要为对象的析构自动插入额外的代码，而且要为异常处理的链条插入代码。这是通常C++函数比C函数可执行代码块更大的缘故。那么如果不用cout，用了try/catch会如何? 我们还是从testPthreadCancel.c的文件开始，进行修改:
```
//testPthreadCancelUnwind.cpp
#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
struct S{
    S(){printf("ctor\n");}
    ~S(){printf("dtor\n");}
};
void* tf(void*arg){
    S obj;
    int oldstate;
    int i=0;
    pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
    while(1){
        printf("sleep1\n");
        sleep(1);
        ++i;
        if(i==3){//response to last cancellation signal
            pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
        }
    }
    return NULL;
}
int main(){
    pthread_t tid;
    pthread_create(&tid,NULL,tf,NULL);
    sleep(2);
    pthread_cancel(tid);
    printf("cancel thread\n");
    pthread_join(tid,NULL);
    return 0;
}
```

不同之处是线程函数里面有一个S的对象，要在函数退出时析构。可以看到析构函数被调用了。
```
ctor
sleep1
sleep1
cancel thread
sleep1
sleep1
dtor
```

看起来一切OK。那么如果栈回滚不是析构函数，而是try/catch呢? 把上面的线程函数改成:
```
void* tf(void*arg){
    try{
        int oldstate;
        int i=0;
        pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
        while(1){
            printf("sleep1\n");
            sleep(1);
            ++i;
            if(i==3){//response to last cancellation signal
                pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
            }
        }
    }catch(...){
        printf("捕获异常\n");
    }
    return NULL;
}
```
运行到setcancelstate之后，马上异常退出:
```
sleep1
sleep1
cancel thread
sleep1
sleep1
捕获异常
FATAL: exception not rethrown
Aborted
```

除非我就像程序提示的那样，在catch里面重新throw这个异常:
```
#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
#include<exception>
using namespace std;
struct S{
    S(){printf("ctor\n");}
    ~S(){printf("dtor\n");}
};
void* tf(void*arg){
    try{
        int oldstate;
        int i=0;
        pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
        while(1){
            printf("sleep1\n");
            sleep(1);
            ++i;
            if(i==3){//response to last cancellation signal
                pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
            }
        }
    }catch(exception& e){
        printf("捕获异常%s\n",e.what());
        sleep(1);
        throw e;
    }
    return NULL;
}
int main(){
    pthread_t tid;
    pthread_create(&tid,NULL,tf,NULL);
    sleep(2);
    pthread_cancel(tid);
    printf("cancel thread\n");
    pthread_join(tid,NULL);
    return 0;
}
```

这一次的运行结果是:
```
sleep1
sleep1
cancel thread
sleep1
sleep1
```

连catch里面的printf都没有了，就像异常被吃掉了一样。换成gcc4.9.2
第一个版本，线程函数里面有S obj;构造，没有try/catch
```
ctor
sleep1
sleep1
cancel thread
sleep1
sleep1
Aborted
```
可以看到和gcc4.1.2结果不同，程序异常退出了，也没有调用对象的析构函数

第二个版本，线程函数里面没有S obj;只有try/catch
```
sleep1
sleep1
cancel thread
sleep1
sleep1
```
没有异常发生，一切正常。那么如果我把S obj放到try/catch的内部呢?
```
void* tf(void*arg){
    try{
        S obj;
        int oldstate;
        int i=0;
        pthread_setcancelstate(PTHREAD_CANCEL_DISABLE, &oldstate);
        while(1){
            printf("sleep1\n");
            sleep(1);
            ++i;
            if(i==3){//response to last cancellation signal
                pthread_setcancelstate(PTHREAD_CANCEL_ENABLE, &oldstate);
            }
        }
    }catch(exception& e){
        printf("捕获异常%s\n",e.what());
        sleep(1);
        throw e;
    }
    return NULL;
}
```

运行结果如下:
```
ctor
sleep1
sleep1
cancel thread
sleep1
sleep1
Aborted
```

可以看到程序的行为完全不一样，因此gcc4.1.2和gcc4.9.2对于pthread线程库的支持，对于栈的处理行为完全不可靠。我们换一个系统，7u的开发机,gcc4.8.5. 第一个版本, 有构造对象没有try/catch:
```
ctor
sleep1
sleep1
cancel thread
sleep1
sleep1
dtor
```
没有问题，第二个版本，只有try/catch，没有对象构造
```
sleep1
sleep1
cancel thread
sleep1
sleep1
```
也没有问题，第三个版本，try/catch里面有对象要构造:
```
ctor
sleep1
sleep1
cancel thread
sleep1
sleep1
dtor
```
一切OK，没有问题。

所以看起来，更像是操作系统版本/pthread版本对于C++程序的兼容性实现开率导致的，在5u的机器上4.9.2都不能工作，而7u上对面的4.8.5却能工作良好。为了进一步确认版本和编译器的影响，实验采用6u系统和gcc4.4版本(vbox虚拟机)。实验结果是3个版本都能无误的工作，和7u的输出一致。可以初步得出的结论是5u的linux系统和pthread库版本过低，升级到新的版本会有改善。

那么mac上面的clang如何? llvm8.0测试上面的代码: 带S obj;语句的版本，无论有没有try/catch，都能工作，输出如下:
```
ctor
sleep1
sleep1
cancel thread
sleep1
sleep1
```
可以看到，析构函数消失了! 也就是mac上pthread退出的时候，根本就不考虑C++的运行时约束。为了比较gcc和clang的行为，在docker环境ubuntu里面做一个测试: gcc6.3.0和clang4.0.0

对于第一个版本，有S obj，没有try/catch，两个编译器编译的结果运行都正常:
```
ctor
sleep1
sleep1
sleep1
cancel thread
sleep1
dtor
```
加上try/catch版本，运行也正常。

综上可以得出，pthread这个C线程库在设计时没有去充分考虑和C++语言代码的运行时兼容性: 在后续的版本中可能已经得到了某种程度的解决(对于Linux而言解决了，其他的系统还没有解决干净)。进一步测试，可以看到pthread_exit有同样的问题:
```
#include<pthread.h>
#include<unistd.h>
#include<stdio.h>
#include<exception>
using namespace std;
struct S{
    S(){printf("ctor\n");}
    ~S(){printf("dtor\n");}
};
void* tf(void*arg){
    S obj;
    pthread_exit(NULL);
    return NULL;
}
int main(){
    pthread_t tid;
    pthread_create(&tid,NULL,tf,NULL);
    sleep(2);
    pthread_cancel(tid);
    printf("cancel thread\n");
    pthread_join(tid,NULL);
    return 0;
}
```
gcc4.1.2测试，没有异常产生:(有S obj,无论有没有try/catch)
```
ctor
dtor
cancel thread
```

改用gcc4.9.2测试以上程序:
```
ctor
Aborted
```

可以看到有异常产生。
mac的clang测试:S obj的dtor没有被调用
```
ctor
cancel thread
```
和之前的一样。没有析构函数的调用。

所以在生产代码里面使用Pthread库，来做C++线程的取消或者退出，是非常有风险的。要避免类似的问题(线程退出，强杀)升级到新的系统是一个可选方案。

那么C++11的新的线程库，易用性远高于pthread，不需要-lpthread，因为它不依赖C的Pthread。那么它对于pthread_cancel/pthread_exit之类的操作是如何处理的呢?

目前的答案是: C++11标准不提供这样的功能!
因为:(https://gcc.gnu.org/ml/gcc-help/2015-08/msg00036.html)
通常一个线程取消的操作，在实现层面需要借助abi::__forced_unwind exception[1]来处理，触发调用者的栈回滚。但是由于C++11的设计中强制规定了所有的析构函数都是noexcept(true)，也就是不抛出exception的(Effective C++强调过在构造和析构函数里面不要抛出异常，因为不会被栈回滚保护)，那么如果析构函数里面有线程取消操作，而这种操作又依赖于操作系统底层的异常机制，那么在设计上就是不可解的。

那么最安全的解决方案? 不要在C++代码里面用pthread_cancel或者pthread_exit，因为不兼容。所以，C++11里面的std::threads是不可interupt的，尽管它不依赖Posix thread。当然std::threads提供了一个native_handle成员变量，也就是各个平台上的线程句柄，可以由用户自己决定是否使用有风险的调用。

MaxCompute框架组
恒名