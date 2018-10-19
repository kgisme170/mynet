#!/usr/bin/env bash
source /etc/profile.d/java1.8.sh
cd $FLUME_HOME
bin/flume-ng agent --conf conf --conf-file conf/spark-flume.conf --name a1
bin/flume-ng agent --conf conf --conf-file conf/spark-flume.conf --name a2
