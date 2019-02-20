cmake_minimum_required (VERSION 2.8)
find_package(Java)
include(UseJava)
enable_testing()

project (HelloWorld)

set(cwd                            "${CMAKE_CURRENT_SOURCE_DIR}"                       )
set(AIName                         "MyHelloWorld"                                      )
set(SRC_DIR                        "${cwd}/src"                                        )

set(MAJOR_VERSION                  0                                                   )
set(MINOR_VERSION                  1                                                   )
set(PATCH_VERSION                  0                                                   )
set(VERSION                        "${MAJOR_VERSION}.${MINOR_VERSION}.${PATCH_VERSION}")

set(CMAKE_JAVA_COMPILE_FLAGS        "-source" "1.8" "-target" "1.8"                    )

set(DAI_JAR_NAME                   "${AIName}-${VERSION}.jar"                          )
set(DAI_JAR_FILE                   "${CMAKE_JAVA_TARGET_OUTPUT_DIR}/${DAI_JAR_NAME}"   )

set( DAI_MANIFEST                   "${CMAKE_CURRENT_BINARY_DIR}/MANIFEST.MF"          )

FILE(GLOB_RECURSE JAVA_SRC_FILES  "${SRC_DIR}/main/java/*.java")
add_jar(
    ${AIName}
    ${JAVA_SRC_FILES}
    VERSION ${VERSION}
    MANIFEST ${DAI_MANIFEST}
)
