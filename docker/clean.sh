#!/bin/bash
cp $HOME/mynet/docker/environment /etc/
source /etc/environment
cd $HOME/mynet/testbed/java && mvn clean && cd $HOME/mynet/testbed/java3p && mvn clean && cd $HOME/mynet/testbed/spring && mvn clean && cd $HOME/mynet/spark && mvn clean && cd $HOME/mynet/hadoop && mvn clean && cd $HOME/mynet/testbed/cpp && scons -c -j8 && rm -fr .sconf_temp/ && rm -fr .sconsign.dblite && cd $HOME/mynet/testbed/cpp3p && ./clean.sh && cd $HOME/mynet/testbed/build/useScons && scons -c -j8 && rm -fr .sconsign.dblite && rm -fr testStaticInfo/.sconsign.dblite && rm -fr testStaticInfo/build/ && cd $HOME/mynet/testbed/build/useAnt && ant clean && cd $HOME/mynet/testbed/build/useBazel/cpp-project && bazel clean --expunge && cd $HOME/mynet/testbed/build/useBazel/java-project && bazel clean --expunge && cd $HOME/mynet/testbed/build/useCmake && rm -fr build && cd $HOME/mynet/testbed/build/useGradle && rm -fr .gradle/ && cd $HOME/mynet/testbed/build/useNinja && ninja -t clean && rm -fr .ninja_deps && rm -fr .ninja_log && cd $HOME/mynet/testbed/build/useSwig && scons -c -j8 && rm -fr .sconsign.dblite && cd $HOME/mynet/testbed/go/cgo && ./clean.sh && cd $HOME/mynet/testbed/linux/module && make clean -j8 && cd $HOME/mynet/testbed/linux/multiplex/linux && bazel clean --expunge && cd $HOME/mynet/testbed/linux/multiplex/posix &&  bazel clean --expunge && cd $HOME/mynet/testbed/linux/multiplex/pthread && bazel clean --expunge && cd $HOME/mynet/testbed/linux/sysv && bazel clean --expunge 
