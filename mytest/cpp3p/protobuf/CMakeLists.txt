cmake_minimum_required(VERSION 2.8)
add_definitions(-std=c++11)

set(BUILD build)
file(MAKE_DIRECTORY ${BUILD})
find_package(Protobuf REQUIRED)

set(HELLO_PB_CC     "${BUILD}/hello.pb.cc")
set(HELLO_PB_H      "${BUILD}/hello.pb.h")
set(EMBED_PB_CC     "${BUILD}/embed.pb.cc")
set(EMBED_PB_H      "${BUILD}/embed.pb.h")
set(SERIALIZE_PB_CC "${BUILD}/serialize.pb.cc")
set(SERIALIZE_PB_H  "${BUILD}/serialize.pb.h")

add_custom_command(
    OUTPUT "${HELLO_PB_CC}"
           "${HELLO_PB_H}"
           "${EMBED_PB_CC}"
           "${EMBED_PB_H}"
           "${SERIALIZE_PB_CC}"
           "${SERIALIZE_PB_H}"
    COMMAND ${PROTOBUF_PROTOC_EXECUTABLE}
    ARGS "--proto_path=."
         "--cpp_out=${BUILD}"
         "hello.proto"
    COMMAND ${PROTOBUF_PROTOC_EXECUTABLE}
    ARGS "--proto_path=."
         "--cpp_out=${BUILD}"
         "embed.proto"
    COMMAND ${PROTOBUF_PROTOC_EXECUTABLE}
    ARGS "--proto_path=."
         "--cpp_out=${BUILD}"
         "serialize.proto"
    COMMENT "genProto"
)

add_custom_target(myPreprocess ALL DEPENDS ${HELLO_PB_CC} ${EMBED_PB_CC} ${SERIALIZE_PB_CC})

add_custom_command(TARGET myPreprocess
  PRE_BUILD 
  COMMAND echo "-->Executing protoc command"
  COMMENT "This command will be executed before building target myPreprocess"
)
link_libraries(${PROTOBUF_LIBRARY})
include_directories(${GENERATED_PROTOBUF_PATH} ${BUILD})

add_executable(pb_hello pb_hello.cpp ${HELLO_PB_CC})
add_executable(pb_hello_stream pb_hello_stream.cpp ${HELLO_PB_CC})
add_executable(pb_embed pb_embed.cpp ${EMBED_PB_CC})
add_executable(pb_serialize pb_serialize.cpp ${SERIALIZE_PB_CC})
