cmake_minimum_required(VERSION 2.8)
add_definitions(-std=c++11)
include_directories(.)

add_custom_target(protoFile
    COMMAND protoc --cpp_out=./ examples.proto
)
add_custom_target(protoSource
    COMMAND protoc --grpc_out=./ --plugin=protoc-gen-grpc=/usr/local/bin/grpc_cpp_plugin examples.proto
)
add_dependencies(protoSource protoFile)

add_library(protoCpp SHARED examples.grpc.pb.cc examples.pb.cc)
add_dependencies(protoCpp protoSource)

link_libraries(protoCpp protobuf grpc grpc++)
add_executable(client client.cpp)
add_executable(server server.cpp)
