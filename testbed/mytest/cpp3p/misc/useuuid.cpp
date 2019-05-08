#include<uuid/uuid.h>
#include<iostream>
using namespace std;
void f(uuid_t id){
    char string[100];
    uuid_unparse(id, string);
    cout<<string<<endl;
}
int main(){
    uuid_t id[4];
    uuid_generate(id[0]);
    uuid_generate_random(id[1]);
    uuid_generate_time(id[2]);
    uuid_generate_time_safe(id[3]);
    f(id[0]);
    f(id[1]);
    f(id[2]);
    f(id[3]);
    return 0;
}