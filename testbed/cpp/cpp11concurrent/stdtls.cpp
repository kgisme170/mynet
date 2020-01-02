#include <iostream>
#include <mutex>
#include <string>
#include <thread>
using namespace std;
struct M {
    int i;
    string s;

    M() : i(20) {}
};
struct N {
    string s;

    N() {}

    ~N() { cout << "N dtor" << endl; }
};
thread_local M obj; 
mutex cout_mutex;
 
void increase_rage(const string& thread_name, int add=1) {
    obj.i += add; // modifying outside a lock is okay; this is a thread-local variable
    lock_guard <mutex> lock(cout_mutex);
    N n;
    cout << "Rage counter for " << thread_name << ": " << obj.i << '\n';
    throw 0;
}

int main() {
    try {
        thread a(increase_rage, "a", 30), b(increase_rage, "b", 40);
        {
            lock_guard <mutex> lock(cout_mutex);
            cout << "Rage counter for main: " << obj.i << '\n';
        }
        a.join();
        b.join();
    } catch (exception &e) {
        cout << e.what() << endl;
    }
    return 0;
}