cmake_minimum_required(VERSION 2.8)
find_package(LIBXML2 CONFIG)

include_directories(${LIBXML2_INCLUDE_DIRS})
link_libraries(${LIBXML2_LIBRARY})

add_executable(testusage testusage.cpp)
