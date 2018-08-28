#!/bin/bash
source /etc/profile.d/java.sh
source /etc/profile.d/hadoop.sh
su - hdfs -c "$HADOOP_HOME/sbin/hadoop-daemon.sh stop namenode && $HADOOP_HOME/sbin/hadoop-daemon.sh stop secondarynamenode && $HADOOP_HOME/sbin/hadoop-daemon.sh stop datanode"
