#include<iostream>
using namespace std;
int global_i;
int main() {
    static int i;
    cout << global_i << i << endl;//打印00
}