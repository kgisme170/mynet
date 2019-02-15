#!/bin/bash
set -x
mkdir -p build
cd build
javac ../cpp2java.java
javah -classpath .. -jni cpp2java
cmake ..
make -j8 VERBOSE=1
CLASSPATH=.. LD_LIBRARY_PATH=. ./usejclass
