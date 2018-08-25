#include <time.h>
#include <string>
#include <iostream>
#include <fstream>
using namespace std;
string f(){
    char wday[][4] = {"Sun", "Mon", "Tue", "Wed", "Thu", "Fri", "Sat"};
    time_t timep;
    struct tm *p;
    time(&timep);
    p = gmtime(&timep);
    char t[128];
    sprintf(t, "%d%d%d%s%d-%d-%d", (1900+p->tm_year), (1+p->tm_mon), p->tm_mday, wday[p->tm_wday], p->tm_hour, p->tm_min, p->tm_sec);
    return t;
}
int main(){
    string cur = f();
    cout<<cur<<endl;
    string fn=f()+".txt";
    fstream f(fn.c_str(), ios::out);
    f<<"abc";
    f.close();
    return 0;
}
