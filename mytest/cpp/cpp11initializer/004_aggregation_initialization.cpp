#include<map>
#include<vector>
using namespace std;
struct A{
    int i;
    struct B{
        int i;
        int j;
    };
}a={2,{3,4}};
int main() {
    int buf[2][2]={1,2,3,4};
    vector<string> vs{"hello","world"};
    map<string, int> msi{{"hello",1},{"world",2}};
    return 0;
}