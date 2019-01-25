#!/bin/bash
find . -name "*.c" -o -name "*.cpp" -o -name "*.h" -o -name "*java" -o -name "*.scala" -o -name "*.md" -o -name "*.py" -o -name "SConstruct" -o -name "SConscript" -o -name "pom.xml" -o -name "*.json" -o -name "*.sh" -o -name "*.txt"|wc -l
