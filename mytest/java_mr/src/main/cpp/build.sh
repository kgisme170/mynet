#!/bin/bash
. /etc/profile.d/java.sh
. /etc/profile.d/hadoop.sh
. $HADOOP_HOME/etc/hadoop/hadoop-env.sh

gcc dfsio.c -I$HADOOP_LIB/include -I$HADOOP_HOME/include -I$JAVA_HOME/include -L$HADOOP_LIB/native -L$JAVA_HOME/jre/lib/amd64/server -ljvm -lhdfs -o dfsio
export CLASSPATH=`hadoop classpath`
export LD_LIBRARY_PATH=$HADOOP_LIB/native:$JAVA_HOME/jre/lib/amd64/server:$LD_LIBRARY_PATH
./dfsio "/tmp/test01"