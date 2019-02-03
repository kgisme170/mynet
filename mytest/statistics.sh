#!/bin/bash
BASEDIR=$(dirname "$0")
echo "find in $BASEDIR file number:"
find $BASEDIR -name "*.c" -o -name "*.cpp" -o -name "*.h" -o -name "*java" -o -name "*.scala" -o -name "*.kt" -o -name "*.kts" -o -name "*.md" -o -name "*.py" -o -name "*.groovy" -o -name "*.gradle" -o -name "SConstruct" -o -name "SConscript" -o -name "pom.xml" -o -name "*.json" -o -name "*.sh" -o -name "*.txt" -o -name "*.yml" -o -name "*.MF" -o -name "*.csv" -o -name "*conf*" -o -name "*site*.xml"|wc -l
