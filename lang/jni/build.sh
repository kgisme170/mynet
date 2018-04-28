#!/bin/bash
javac cpp2java.java && javah -jni cpp2java && scons -u && cp build/libimpl.so ./ && cp build/usejclass ./ && usejclass