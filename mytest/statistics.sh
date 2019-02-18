#!/bin/bash
BASEDIR=$(dirname "$0")
echo "find in $BASEDIR file number:"
find $BASEDIR -name "*.c" -o -name "*.cpp" -o -name "*.h" -o -name "*java" -o -name "*.scala" -o -name "*.kt" -o -name "*.kts" -o -name "*.md" -o -name "*.py" -o -name "*.groovy" -o -name "*.gradle" -o -name "SConstruct" -o -name "SConscript" -o -name "*.xml" -o -name "*.json" -o -name "*.sh" -o -name "*.txt" -o -name "*.yml" -o -name "*.MF" -o -name "*.csv" -o -name "*conf*" -o -name "*.policy" -o -name "*.rb" -o -name "MANIFEST.MF" -o -name "*.erl" -o -name "*.Command" -o -name "*.lua" -o -name "*.proto" -o -name "*.thrift" -o -name "BUILD"|wc -l
