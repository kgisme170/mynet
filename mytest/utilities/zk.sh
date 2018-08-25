export ZKHOME='/opt/zookeeper-3.4.12'
export PATH=$ZKHOME/bin:$PATH
. $ZKHOME/bin/zkEnv.sh
export CLASSPATH=$ZKHOME/zookeeper-3.4.12.jar:/opt/yetus-0.7.0/lib/audience-annotations/audience-annotations-0.7.0.jar:$CLASSPATH

