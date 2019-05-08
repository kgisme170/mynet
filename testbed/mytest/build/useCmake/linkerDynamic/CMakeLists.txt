add_library(b SHARED b.cpp)
add_library(d SHARED d.cpp)
add_executable(a a.cpp)
target_link_libraries(a b d)
