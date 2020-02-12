PB=proto/toupper.pb.go

all: client server

server: $(PB)
	 go build main/server.go
client: $(PB)
	 go build main/client.go
$(PB):
	 protoc -I proto toupper.proto --go_out=plugins=grpc:proto

clean:
	 rm -f $(PB) client server
