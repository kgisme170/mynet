cmake_minimum_required(VERSION 2.8)
find_package(LibXml2)

include_directories(${LIBXML2_INCLUDE_DIRS})
link_libraries(${LIBXML2_LIBRARY})

add_executable(testusage testusage.cpp)
