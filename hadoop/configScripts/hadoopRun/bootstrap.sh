#!/bin/bash
#root user!

groupadd hadoop
useradd -g hadoop yarn
useradd -g hadoop hdfs
useradd -g hadoop mapred

mkdir -p /var/data/hadoop/hdfs/nn
mkdir -p /var/data/hadoop/hdfs/snn
mkdir -p /var/data/hadoop/hdfs/dn
chown -R hdfs:hadoop /var/data/hadoop/hdfs

mkdir -p /var/log/hadoo/yarn
chown -R yarn:hadoop /var/log/hadoo/yarn

cd $HADOOP_HOME
mkdir logs
chmod g+w logs
chown -R yarn:hadoop

cp -f config/site.xml $HADOOP_HOME/etc/hadoop/
cp -f config/hdfs-site.xml $HADOOP_HOME/etc/hadoop/
cp -f config/mapred-site.xml $HADOOP_HOME/etc/hadoop/
cp -f config/yarn-site.xml $HADOOP_HOME/etc/hadoop/
cp -f config/hadoop-env.sh $HADOOP_HOME/etc/hadoop/
cp -f config/mapred-env.sh $HADOOP_HOME/etc/hadoop/
cp -f config/yarn-env.sh $HADOOP_HOME/etc/hadoop/

su - hdfs -c "cd $HADOOP_HOME/bin && hdfs namenode -format"
