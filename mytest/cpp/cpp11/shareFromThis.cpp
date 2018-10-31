#include<iostream>
#include<memory>
using namespace std;
struct Foo : public std::enable_shared_from_this<Foo> {
    int m_i;
    Foo():m_i(3) { std::cout << "Foo::Foo\n"; }
    ~Foo() { std::cout << "Foo::~Foo\n"; }
    std::shared_ptr<Foo> getFoo() { return shared_from_this(); }
    void print(){cout<<m_i<<endl;}
};
int main() {
    Foo *f = new Foo;
    //std::shared_ptr<Foo> pf1;

    {
        std::shared_ptr<Foo> pf2(f);
        //pf1 = pf2->getFoo();  // shares ownership of object with pf2
    }

    std::cout << "pf2 is gone\n";
    f->print();
    return 0;
}
