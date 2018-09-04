#!/bin/bash
find . -type f -iname '*.cpp' -or -iname '*.h' -or -iname '*.cc' |grep -v third_party|grep -v thirdparty|grep -v "build/release64" |grep -v "\./bin/" |grep -v "\./package"|grep -v base_apsara|grep -v "\./llvm"|grep -v "\./hive"|grep -v "./.odps_build_dependencies"|grep -v "\./apsara/"|sort