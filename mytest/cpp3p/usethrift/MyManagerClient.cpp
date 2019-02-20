#include <iostream>
#include <thrift/protocol/TBinaryProtocol.h>
#include <thrift/transport/TSocket.h>
#include <thrift/transport/TTransportUtils.h>

#include "build/gen-cpp/MyManager.h"

using namespace std;
using namespace apache::thrift;
using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;

using namespace worker;

int main() {
  boost::shared_ptr <TTransport> socket(new TSocket("localhost", 9090));
  boost::shared_ptr <TTransport> transport(new TBufferedTransport(socket));
  boost::shared_ptr <TProtocol> protocol(new TBinaryProtocol(transport));
  MyManagerClient client(protocol);

  try {
    transport->open();

    client.ping();
    cout << "ping()" << endl;
    transport->close();
  } catch (TException &tx) {
    cout << "ERROR: " << tx.what() << endl;
  }
}