#include "embed.pb.h"
#include<fstream>
#include<iostream>
using namespace std;
//package决定了.h/.cpp中的namespace
//optional和required都可以有[default]属性
int main()
{
    GOOGLE_PROTOBUF_VERIFY_VERSION;
    fstream f("./log4.data",ios::binary|ios::out);
    Person p;
    p.set_name("__myname_abc__");
    p.set_id(101);//hex 65
    Person::PhoneNumber* p1=p.add_phone();
    p1->set_number("256");
    Person::PhoneNumber* p2=p.add_phone();
    p2->set_number("512");
    p.SerializeToOstream(&f);
    cout<<"phone size="<<p.phone_size()<<endl;
    cout<<p.DebugString()<<endl;
    //google::protobuf::ShutdownProtobufLibrary();
    return 0;
}
