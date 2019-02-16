#include "hello.pb.h"
#include<fstream>
#include<iostream>
#include<string>
using namespace std;
#include <google/protobuf/io/zero_copy_stream_impl.h>
#include <google/protobuf/io/coded_stream.h>

using namespace google::protobuf::io;

class FASWriter {
    fstream mFs;
    OstreamOutputStream *_OstreamOutputStream;
    CodedOutputStream *_CodedOutputStream;
public:
    FASWriter(const std::string &file) {
        mFs.open(file.c_str(), std::ios_base::out | std::ios_base::binary);
        assert(mFs.good());

        _OstreamOutputStream = new OstreamOutputStream(&mFs);
        _CodedOutputStream = new CodedOutputStream(_OstreamOutputStream);
    }

    inline void operator()(const ::google::protobuf::Message &msg) {
        _CodedOutputStream->WriteVarint32((unsigned) msg.ByteSize());

        if (!msg.SerializeToCodedStream(_CodedOutputStream))
            std::cout << "SerializeToCodedStream error " << std::endl;
    }

    ~FASWriter() {
        delete _CodedOutputStream;
        delete _OstreamOutputStream;
        mFs.close();
    }
};

class FASReader {
    std::ifstream mFs;

    IstreamInputStream *_IstreamInputStream;
    CodedInputStream *_CodedInputStream;
public:
    FASReader(const std::string &file) : mFs(file.c_str(), std::ios::in | std::ios::binary) {
        assert(mFs.good());

        _IstreamInputStream = new IstreamInputStream(&mFs);
        _CodedInputStream = new CodedInputStream(_IstreamInputStream);
    }

    template<class T>
    bool ReadNext() {
        T msg;
        unsigned int size;

        bool ret = _CodedInputStream->ReadVarint32(&size);
        if (ret) {
            CodedInputStream::Limit msgLimit = _CodedInputStream->PushLimit(size);
            ret = msg.ParseFromCodedStream(_CodedInputStream);
            if (ret) {
                _CodedInputStream->PopLimit(msgLimit);
                std::cout << " FASReader ReadNext: " << msg.DebugString() << std::endl;
            }
        }

        return ret;
    }

    ~FASReader() {
        delete _CodedInputStream;
        delete _IstreamInputStream;
        mFs.close();
    }
};
using namespace std;
int main() {
    hello p1, p2, p3;
    p1.set_f1(1);
    p1.set_f2(2);
    p2.set_f1(3);
    p2.set_f2(4);
    p3.set_f1(5);
    p3.set_f2(6);
    {
        FASWriter writer("./hello.data");
        writer(p1);
        writer(p2);
        writer(p3);
    }
    {
        FASReader reader("./hello.data");
        while (reader.ReadNext<hello>()) {}
    }
    return 0;
}