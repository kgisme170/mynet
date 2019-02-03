struct A{};
int main()
{
    A obj; //obj是左值
    A a2 = obj; //obj是右值
    A a3 = A(); //A()构造了一个右值
    return 0;
}