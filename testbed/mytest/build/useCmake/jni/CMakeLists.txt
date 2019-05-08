cmake_minimum_required (VERSION 2.8) 
add_custom_target(classFile
    COMMAND javac cpp2java.java
)
add_custom_target(jniHeader
    COMMAND javah -classpath . -jni cpp2java
)
add_dependencies(jniHeader classFile)

include_directories(.)
add_library(impl SHARED impl.c)
add_dependencies(impl jniHeader)
include_directories(usejclass PRIVATE $ENV{JAVA_HOME}/include)
include_directories(usejclass PRIVATE $ENV{JAVA_HOME}/include/linux)
link_directories($ENV{JAVA_HOME}/jre/lib/amd64/server)
add_executable(usejclass usejclass.cpp useJvm.cpp)
target_link_libraries(usejclass jvm)
