cmake_minimum_required(VERSION 2.8)
add_definitions(-std=c++11)

set(BUILD build)
file(MAKE_DIRECTORY ${BUILD})
find_package(Protobuf REQUIRED)

set(EXAMPLES_PROTO examples.proto)
set(EXAMPLES_PB_CC      ${BUILD}/examples.pb.cc)
set(EXAMPLES_PB_H       ${BUILD}/examples.pb.h)
set(EXAMPLES_GRPC_PB_CC ${BUILD}/examples.grpc.pb.cc)
set(EXAMPLES_GRPC_PB_H  ${BUILD}/examples.grpc.pb.h)

add_custom_command(
    OUTPUT "${EXAMPLES_PB_CC}"
	   "${EXAMPLES_PB_H}"
	   "${EXAMPLES_GRPC_PB_CC}"
	   "${EXAMPLES_GRPC_PB_H}"
    COMMAND ${PROTOBUF_PROTOC_EXECUTABLE}
    ARGS "--proto_path=."
         "--cpp_out=${BUILD}"
	 "${EXAMPLES_PROTO}"
    COMMAND ${PROTOBUF_PROTOC_EXECUTABLE}
    ARGS "--proto_path=."
         "--grpc_out=${BUILD}"
         "--plugin=protoc-gen-grpc=/usr/local/bin/grpc_cpp_plugin"
	 "${EXAMPLES_PROTO}"
    COMMENT "genProto"
)

add_custom_target(myPreprocess ALL DEPENDS ${EXAMPLES_PB_CC} ${EXAMPLES_GRPC_PB_CC})

add_custom_command(TARGET myPreprocess
  PRE_BUILD 
  COMMAND echo "Executing protoc command"
  COMMENT "This command will be executed before building target myPreprocess"
)
include_directories(${BUILD})
link_libraries(grpc++_unsecure grpc gpr ${PROTOBUF_LIBRARY})

add_library(examples ${EXAMPLES_PB_CC} ${EXAMPLES_GRPC_PB_CC})
add_executable(grpc_client client.cpp)
target_link_libraries(grpc_client examples)
add_executable(grpc_server server.cpp)
target_link_libraries(grpc_server examples)
