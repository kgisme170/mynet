#learn cmake from https://www.hahack.com/codes/cmake/
cmake_minimum_required(VERSION 2.8)
add_subdirectory(basic)
add_subdirectory(dependency)
add_subdirectory(subdir)
add_subdirectory(transmitDeps)
add_subdirectory(linkerConstructor)
add_subdirectory(linkerDoubleDefine)
add_subdirectory(linkerDynamic)
add_subdirectory(linkerExtension)
add_subdirectory(linkerPreload)
add_subdirectory(useLlvm)
if(UNIX)
    execute_process(
        COMMAND ls /lib/modules
        OUTPUT_VARIABLE MY_VERSIONED_DIR 
    )
endif()
