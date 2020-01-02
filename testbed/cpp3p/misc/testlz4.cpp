#include <fstream>
#include <iostream>
#include "lz4.h"
using namespace std;
int main() {
    char str[] = "aabbbcccddddeeeeffffffggggggghhhhhhhh";
    size_t len = sizeof(str);
    char *target = new char[len];
    int size = LZ4_compress_default((const char *) (&str), target, len, len);
    ofstream os("my.dat", ofstream::binary);
    os.write(target, size);
    os.close();
    delete[] target;
    target = 0;

    ifstream is("my.dat", ifstream::binary);
    char *in = new char[len];
    is.read(in, size);
    cout << "Byte number:" << size << ",bytes read:" << in << endl;
    is.close();
    return 0;
}