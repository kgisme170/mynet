#!/bin/bash
source /etc/profile.d/java.sh
source /etc/profile.d/hadoop.sh
su - hdfs -c "$HADOOP_HOME/sbin/hadoop-daemon.sh start namenode && $HADOOP_HOME/sbin/hadoop-daemon.sh start secondarynamenode && $HADOOP_HOME/sbin/hadoop-daemon.sh start datanode"
