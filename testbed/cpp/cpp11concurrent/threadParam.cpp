#include<functional>
#include<iostream>
#include<thread>
using namespace std;
void f(int& j, int& k){
    j+=1;
    k+=1; 
}
int main(){
    int j=3;
    int k=4;
    thread t1(f, ref(j), ref(k));
    t1.join();
    cout<<j<<','<<k<<endl;
    thread t2(f, ref(j), ref(k));
    t2.join();
    cout<<j<<','<<k<<endl;
    return 0;
}
