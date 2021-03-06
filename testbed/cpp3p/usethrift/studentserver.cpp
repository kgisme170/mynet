#include "Serv.h"
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/server/TSimpleServer.h>
#include <thrift/transport/TServerSocket.h>
#include <thrift/transport/TBufferTransports.h>

using namespace ::apache::thrift;
using namespace ::apache::thrift::protocol;
using namespace ::apache::thrift::transport;
using namespace ::apache::thrift::server;

using boost::shared_ptr;

class ServHandler : virtual public ServIf {
public:
    ServHandler() {
      // Your initialization goes here
    }

    int32_t put(const Student &s) {
      // Your implementation goes here
      printf("put,no=%d,name=%s,gendor=%d,age=%d\n",
             s.no,
             s.name.c_str(),
             s.gendor,
             s.age);
    }
};

int main(int argc, char **argv) {
  int port = 9090;
  shared_ptr <ServHandler> handler(new ServHandler());
  shared_ptr <TProcessor> processor(new ServProcessor(handler));
  shared_ptr <TServerTransport> serverTransport(new TServerSocket(port));
  shared_ptr <TTransportFactory> transportFactory(new TBufferedTransportFactory());
  shared_ptr <TProtocolFactory> protocolFactory(new TBinaryProtocolFactory());

  TSimpleServer server(processor, serverTransport, transportFactory, protocolFactory);
  server.serve();
  return 0;
}