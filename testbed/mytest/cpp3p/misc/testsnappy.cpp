#include"snappy.h"
#include<iostream>
#include<string>
using namespace std;
int main() {
    string input = "abbcccddddeeeeeffffffggggggghhhhhhhhiiiiiiiiiiiiiiiiiiiiiiiijjjjjjjjjjjjjjjjj";
    string output;
    snappy::Compress(input.data(), input.length(), &output);
    cout << output << endl;
    return 0;
}