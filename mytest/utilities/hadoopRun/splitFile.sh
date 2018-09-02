#!/bin/bash
usage()
{
    echo Usage:
    echo $(basename $0) inputFileName recordLineSeparator
}
[[ $# -ne 2 ]] && usage && exit 1
INPUTFILE=$1
RECORDSEPARATOR=$2
awk -v RS="\n${RECORDSEPARATOR}\n" '{print > "file"++c}' ${INPUTFILE}