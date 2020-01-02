#include <iostream>
#include <memory>
#include <string>
using namespace std;
int i=0;
class G {
    G(const G &g) {}

    char *m_str;
public:
    G() {
        ++i;
        m_str = new char[200];
        if (i > 2)throw int(1);
        cout << "default\n";
    }

    ~G() {
        --i;
        if (m_str)delete[]m_str;
        cout << "destructor\n";
    }
};

int main(void) {
    try {
        auto obj1 = make_shared<G>();
        auto obj2 = make_shared<G>();
        auto obj3 = make_shared<G>();
        auto obj4 = make_shared<G>();
    } catch (...) {
        cout << "i=" << i << endl;
    }
    return 0;
}