struct A{};
int main() {
    A a1();//a1是一个函数，most vexing parse导致的
    A a2{};//a2是一个对象，初始化。
}