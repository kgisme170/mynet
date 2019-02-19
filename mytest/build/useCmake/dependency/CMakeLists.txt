cmake_minimum_required(VERSION 2.8)
#add_executable(demo, main.cpp, power.cpp)

add_executable(main main.cc)
add_library(mylib mylib.cc)
target_link_libraries(main mylib)
