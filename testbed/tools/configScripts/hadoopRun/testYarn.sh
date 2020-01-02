#!/bin/bash
source /etc/profile.d/java.sh
source /etc/profile.d/hadoop.sh
export HADOOP_EXAMPLES=$HADOOP_HOME/share/hadoop/mapreduce
su - hdfs -c "yarn jar $HADOOP_EXAMPLES/hadoop-mapreduce-examples-2.8.4.jar pi 16 1000"
