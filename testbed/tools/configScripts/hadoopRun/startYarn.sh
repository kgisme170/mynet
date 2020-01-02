#!/bin/bash
source /etc/profile.d/java.sh
source /etc/profile.d/hadoop.sh
su - yarn -c "$HADOOP_HOME/sbin/yarn-daemon.sh start resourcemanager && $HADOOP_HOME/sbin/yarn-daemon.sh start nodemanager && $HADOOP_HOME/sbin/mr-jobhistory-daemon.sh start historyserver"
