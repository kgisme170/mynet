#!/bin/bash
for f in `cat ~/temp/filemakepair.txt`
do
    cat $f | sed 's/make_pair<.*>(/make_pair(/' > ~/temp/f.temp
    mv ~/temp/f.temp $f
done