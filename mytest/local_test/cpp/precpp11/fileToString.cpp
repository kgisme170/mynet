#include<fstream>
#include<iostream>
#include<sstream>
using namespace std;
int main(){
    char fn[] = "instanceIds.txt";
    ofstream f(fn);
    for (size_t i = 0; i < 10; ++ i) {
        f << "20171026114246691ggyx3t81" << ';';
    }
    f.close();

    ifstream f2(fn);
    string line;
    while(std::getline(f2, line, ';')) {
        cout << line << '\n';
    }
    cout << "----------------------\n";
    ostringstream oss;
    for (size_t i = 0; i < 10; ++ i) {
        oss << "20171026114246691ggyx3t81" << ';';
    }

    string s = oss.str();
    istringstream iss(s);
    cout << s << '\n';
    cout << "----------------------\n";
    while(std::getline(iss, line, ';')) {
        cout << line << '\n';
    }

    return 0;
}
