cmake_minimum_required(VERSION 3.8)
project(demo)
#add_executable(demo, main.cpp, power.cpp)

aux_source_directory(. allSources)
add_executable(demo, allSources)