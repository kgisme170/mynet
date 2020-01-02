#!/usr/bin/env bash
source /etc/profile.d/java1.8.sh
cd $FLUME_HOME
bin/flume-ng agent --conf conf --conf-file conf/spark-flume.conf --name a1
bin/flume-ng agent --conf conf --conf-file conf/spark-flume.conf --name a2

spark-submit --class useFlume target/streaming-1.0-SNAPSHOT.jar
#注意要启动conf文件中的所有agent,否则spark的flume任务启动的时候会不停的抛出异常