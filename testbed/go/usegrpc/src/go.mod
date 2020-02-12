module usegrpc

go 1.13

replace golang.org/x/net v0.0.0-20181023162649-9b4f9f5ad519 => github.com/golang/net v0.0.0-20181023162649-9b4f9f5ad519

replace golang.org/x/tools v0.0.0-20181221001348-537d06c36207 => github.com/golang/tools v0.0.0-20181221001348-537d06c36207

require (
	github.com/golang/protobuf v1.3.3
	google.golang.org/grpc v1.27.1
)
