cmake_minimum_required(VERSION 2.8)
add_definitions(-std=c++11)
include_directories(.)

find_program(FoundProtoc protoc)
if(FoundProtoc)
    execute_process(
        COMMAND ${FoundProtoc} --cpp_out=./ examples.proto
    )
    execute_process(
        COMMAND ${FoundProtoc} --grpc_out=./ --plugin=protoc-gen-grpc=/usr/local/bin/grpc_cpp_plugin examples.proto
    )

    add_library(protoCpp SHARED examples.grpc.pb.cc examples.pb.cc)
    link_libraries(protoCpp protobuf grpc grpc++)
    add_executable(client client.cpp)
    add_executable(server server.cpp)
endif()
