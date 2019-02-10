add_executable(doubledefineCpp main.cpp doubledefine01.cpp doubledefine02.cpp)

add_library(mydynamic SHARED doubledefine01.cpp doubledefine02.cpp)
add_library(mystatic STATIC doubledefine01.cpp doubledefine02.cpp)
add_executable(dynamicDD main.cpp)
add_executable(staticDD main.cpp)
target_link_libraries(dynamicDD mydynamic)
target_link_libraries(staticDD mystatic)
