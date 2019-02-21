cmake_minimum_required(VERSION 2.8)
add_executable(testlz4 testlz4.cpp)
target_link_libraries(testlz4 lz4)

add_executable(testsnappy testsnappy.cpp)
target_link_libraries(testsnappy snappy)

add_executable(testzstd testzstd.cpp)
target_link_libraries(testzstd zstd)

add_executable(useuuid useuuid.cpp)
target_link_libraries(useuuid uuid)

add_executable(testleveldb testleveldb.cpp)
target_link_libraries(testleveldb leveldb snappy pthread)
