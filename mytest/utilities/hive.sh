export SPARK_HOME=/opt/spark-1.6.3-bin-hadoop2.6
export HIVE_HOME=/opt/apache-hive-2.3.3-bin;export PATH=$HIVE_HOME/bin:$PATH
export CLASSPATH=$SPARK_HOME/lib/spark-assembly-1.6.3-hadoop2.6.0.jar:$SPARK_HOME/lib/datanucleus-api-jdo-3.2.6.jar:$SPARK_HOME/lib/datanucleus-core-3.2.10.jar:$SPARK_HOME/lib/datanucleus-rdbms-3.2.9.jar:$SPARK_HOME/lib/spark-1.6.3-yarn-shuffle.jar:$SPARK_HOME/lib/spark-examples-1.6.3-hadoop2.6.0.jar:.:$CLASSPATH
