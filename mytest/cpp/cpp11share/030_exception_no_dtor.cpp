#include<memory>
#include<iostream>
using namespace std;
int i;
struct M{
    M(){ cout<<"M ctor"<<endl; }
    ~M(){cout<<"M dtor"<<endl; }
};
struct N{
    N(){
        cout<<"N ctor"<<endl;
        shared_ptr<M> empty;
        if(i==1){
            cout<<"before throw:i="<<i<<endl;
            empty.reset(new M());
            throw 1;
        }else{
            empty.reset(new M());
            cout<<"no throw:i="<<i<<endl;
        }
    }
    ~N(){ cout<<"N dtor"<<endl; }
};
int main(int argc,char*argv[]){
    i = argc;
    N obj;
    return 0;
}
//N ctor
//before throw:i=1
//M ctor
//terminate called after throwing an instance of 'int'
//Aborted
