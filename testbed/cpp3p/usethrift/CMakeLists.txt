cmake_minimum_required(VERSION 2.8)
add_definitions(-std=c++11)
include_directories(.)

set(directory build)
file(MAKE_DIRECTORY ${directory})
find_program(FoundThrift thrift)

if(FoundThrift)
    execute_process(
	COMMAND ${FoundThrift} -r --gen cpp -o build MyManager.thrift
    )
    execute_process(
        COMMAND ${FoundThrift} -r --gen cpp -o build student.thrift
    )
    
    include_directories(/usr/local/include)
    link_directories(/usr/local/lib)
    link_libraries(thrift thriftnb event)
    
    set(GEN ${directory}/gen-cpp)
    
    add_executable(MyManager_client ${GEN}/MyManager.cpp MyManagerClient.cpp)
    add_executable(MyManager_server ${GEN}/MyManager.cpp ${GEN}/MyManager_server.skeleton.cpp)
endif()
