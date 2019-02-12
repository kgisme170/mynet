cmake_minimum_required (VERSION 2.8)

#find_package(Java REQUIRED)
include(UseJava)

#enable_testing()
#project (HelloWorld)
set(CMAKE_JAVA_COMPILE_FLAGS "-source" "1.8" "-target" "1.8")

add_jar(HelloWorld MANIFEST ./MANIFEST)

get_target_property(_jarFile HelloWorld JAR_FILE)
get_target_property(_classDir HelloWorld CLASSDIR)
message(STATUS "Jar file ${_jarFile}")
message(STATUS "Class compiled to ${_classDir}")
