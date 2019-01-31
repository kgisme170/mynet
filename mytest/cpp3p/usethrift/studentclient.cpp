#include "build/gen-cpp/Serv.h"  // 替换成你的.h  
#include <transport/TSocket.h>
#include <transport/TBufferTransports.h>
#include <protocol/TBinaryProtocol.h>
using namespace apache::thrift;
using namespace apache::thrift::protocol;
using namespace apache::thrift::transport;
using boost::shared_ptr;
int main()
{
	boost::shared_ptr<TSocket> socket(new TSocket("localhost", 9090));
	//对接nonblockingServer时必须boost::shared_ptr<TTransport> transport(new TFramedTransport(socket)); 
    boost::shared_ptr<TTransport> transport(new TBufferedTransport(socket));//普通server端时用
	boost::shared_ptr<TProtocol> protocol(new TBinaryProtocol(transport));
	ServClient client(protocol);
	
	//设置发送、接收、连接超时
	socket->setConnTimeout(2000);
	socket->setRecvTimeout(2000);
	socket->setSendTimeout(2000);
	
	transport->open();

	Student stu;
	stu.no = 1;
    stu.name = "lm";
	stu.gendor = 0;
	stu.age = 25;
	int ret = client.put(stu);
	printf("客户端 put 返回值=%d\n", ret);
	transport->close();
	return 0;
}
