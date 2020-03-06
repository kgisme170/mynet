#include<iostream>
class Impl;
class My
{
public:
    template<class T = Impl> // default template argument is for cpp++ and above
    T get() const;
};

class Impl
{
    My const& owner_;
public:
    Impl(My const& owner) : owner_(owner) {}
    operator int() const;
    operator short() const;
};

template<>
Impl My::get<Impl>() const { return *this; }

template<>
int My::get<int>() const { return 2; }

template<>
short My::get<short>() const { return 3; }

Impl::operator int() const {
    return owner_.get<int>();
}
Impl::operator short() const {
    return owner_.get<short>();
}

int main() {
    My m;
    int i_ = m.get();
    short s_ = m.get();
    std::cout << i_ << ", " << s_ << '\n';
}
