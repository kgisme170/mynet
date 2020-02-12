PB=toupper.pb.go

all: client server

server: $(PB)
	go build server.go
client: $(PB)
	go build client.go
$(PB):
	protoc --go_out=plugins=grpc:. toupper.proto

clean:
	rm -f $(PB) client server
