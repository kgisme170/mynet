#include <atomic>
#include <iostream>
using namespace std;

struct Big {
    int i;
    int j;
    int k[100];
};

int main() {
  Big value, expect;
  atomic <Big> ab;
  ab.compare_exchange_weak(expect, value);
  return 0;
}