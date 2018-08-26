subdir = ./

export PKG_CONFIG_PATH=/usr/local/lib/pkgconfig

SOURCES = $(wildcard $(subdir)*.cc)
SRCOBJS = $(patsubst %.cc,%.o,$(SOURCES))
CC = g++

%.o:%.cc
	$(CC) -std=c++11 -I/usr/local/include -c $< -o $@

%.o:%.cpp
	$(CC) -std=c++11 -I/usr/local/include -c $< -o $@

all: client server

client:	examples.grpc.pb.o examples.pb.o examples_client.o
	$(CC) $^ `pkg-config --libs protobuf grpc++ grpc` -ldl -lssl -o $@

server:	examples.grpc.pb.o examples.pb.o examples_server.o
	$(CC) $^ `pkg-config --libs protobuf grpc++ grpc` -ldl -lssl -o $@
#chmod 777 $@

clean:
	rm -f *.o
	rm -f client
	rm -f server
