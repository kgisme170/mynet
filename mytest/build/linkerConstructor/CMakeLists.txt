cmake_minimum_required (VERSION 2.8)
add_library(myclasses SHARED class1.cpp class2.cpp)
add_executable(TestConstructor main.cpp)
target_link_libraries(TestConstructor myclasses)
