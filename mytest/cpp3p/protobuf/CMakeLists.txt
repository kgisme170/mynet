cmake_minimum_required(VERSION 2.8)
add_definitions(-std=c++11)
include_directories(.)

set(directory build)
file(MAKE_DIRECTORY ${directory})
find_program(FoundProtoc protoc)

if(FoundThrift)
    execute_process(
	COMMAND ${FoundProtoc} --cpp_out=./ hello.proto
    )
    execute_process(
        COMMAND ${FoundProtoc} --cpp_out=./ embed.proto
    )
    execute_process(
        COMMAND ${FoundProtoc} --cpp_out=./ serialize.proto
    )
    execute_process(
        COMMAND ${FoundProtoc} --cpp_out=./ pytest.proto
    )
    
    include_directories(/usr/local/include)
    link_directories(/usr/local/lib)
    link_libraries(thrift thriftnb event)
    
    add_executable(pb_hello        pb_hello.cpp        hello.pb.cc)
    add_executable(pb_hello_stream pb_hello_stream.cpp hello.pb.cc)
    add_executable(pb_embed        pb_embed.cpp        embed.pb.cc)
    add_executable(pb_serialize    pb_serialize.cpp    serialize.pb.cc)
endif()
