struct A{};
struct B:A{};

template<class T>
void f(T t){}
int main(){
    f({1,2,3});//语法错误

    auto x1={A(),A()};//OK
    auto x2={A(),B()};//语法错误
    return 0;
}