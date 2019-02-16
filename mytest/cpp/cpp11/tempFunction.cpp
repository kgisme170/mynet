#include<iostream>
#include<functional>
using namespace std;
struct F {
    const std::function<void()> &myF;

    F(const std::function<void()> &f) : myF(f) {}

    void call() {
        myF();
    }
};
int main() {
    F f([] { cout << "Hello World!" << endl; });
    f.call();
    return 0;
}