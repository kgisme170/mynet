cmake_minimum_required(VERSION 2.8)
find_package(LLVM CONFIG)

include_directories(${LLVM_INCLUDE_DIRS})
add_executable(llvm_gen llvm_gen.cpp)
target_link_libraries(llvm_gen ${LLVM_LIBRARIES})
