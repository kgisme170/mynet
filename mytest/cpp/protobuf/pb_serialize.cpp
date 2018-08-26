#include "serialize.pb.h"
#include<fstream>
#include<iostream>
#include<string>
using namespace std;
using namespace lm;
void f(const char* fn){
    cout<<"Parsing file:"<<fn<<endl;
    lm::hello msg;
    fstream f1(fn, ios::in|ios::binary);
    if (!msg.ParseFromIstream(&f1)) {
        cerr<<"Failed to parse:<<"<<fn<<endl;
    }
    cout<<boolalpha;
    cout<<msg.id()<<endl;
    cout<<msg.str()<<endl;
    cout<<msg.op()<<endl;
}
int main(){
    hello msg1;
    cout<<hello::kStrFieldNumber<<hello::kIdFieldNumber<<endl;
    msg1.set_id(101);
    msg1.set_str("hello");
    fstream output("./log2.data",ios::out|ios::trunc|ios::binary);
    msg1.SerializeToOstream(&output);

    string str;
    msg1.SerializeToString(&str);
    cout<<"str="<<str<<endl;
    cout<<"bytesize="<<msg1.ByteSize()<<endl;
    cout<<"cached size="<<msg1.GetCachedSize()<<endl;
    char*buf=new(std::nothrow)char[msg1.GetCachedSize()+1];
    buf[msg1.GetCachedSize()]=0;
    msg1.SerializeWithCachedSizesToArray((google::protobuf::uint8*)buf);
    cout<<buf<<endl;
    delete[] buf;
    const hello& p=hello::default_instance();
    cout<<p.op()<<endl;
    cout<<p.id()<<","<<p.str()<<","<<p.op()<<endl;
    output.close();
    f("log2.data");
    return 0;
}
