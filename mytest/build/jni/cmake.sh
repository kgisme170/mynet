#!/bin/bash
javac cpp2java.java
javah -jni cpp2java
cmake .
make -j8 VERBOSE=1
LD_LIBRARY_PATH=. ./usejclass
