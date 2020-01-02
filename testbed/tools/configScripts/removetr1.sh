#!/bin/bash
[ $# -ne 1 ] && echo "输入一个文件列表作为参数!" && exit 1
fileList=$1
for f in `cat $fileList`
do
    grep tr1 $f
    if [ $? -eq 0 ]
    then
        echo "替换 $f"
        cat $f | sed 's/<tr1\//</' | sed '/using namespace std::tr1/d' | sed '/using namespace tr1/d'|sed 's/tr1:://'> ~/temp/f.temp
        mv ~/temp/f.temp $f
    fi
done