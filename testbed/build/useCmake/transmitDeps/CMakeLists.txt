add_library(mya SHARED a.cpp)
add_library(myb SHARED b.cpp)
add_library(myc SHARED c.cpp)
add_executable(main main.cpp)
target_link_libraries(main mya)
target_link_libraries(mya myb)
target_link_libraries(mya myc)
add_executable(dl dl.cpp)
target_link_libraries(dl -ldl)
