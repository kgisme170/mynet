#!/bin/bash
apt-get -y install -y tcl tcl-dev
apt-get -y install --linux-headers-$(uname -r) libelf-dev
source /etc/environment
# ant
cd /mynet/testbed/build/useAnt && ant && cd /mynet/testbed/build/useBazel/cpp-project && chmod +x ./run.sh && ./run.sh && cd /mynet/testbed/build/useBazel/java-project && chmod +x ./run.sh && ./run.sh && cd /mynet/testbed/build/useCmake && cmake . && make -j8 && cd /mynet/testbed/build/useGradle && gradle && cd /mynet/testbed/build/useMaven && mvn package && cd /mynet/testbed/build/useNinja && ninja && cd /mynet/testbed/build/useScons && scons -j8 && cd /mynet/testbed/build/useScons/testStaticInfo && scons -j8 && cd /mynet/testbed/build/useSwig && scons -j8 && cd /mynet/testbed/cpp && chmod +x testgmock.sh && scons -j8 && cd /mynet/testbed/cpp3p && chmod +x build.sh && ./build.sh && cd /mynet/testbed/linux/multiplex/linux && bazel build ...  && cd /mynet/testbed/linux/multiplex/posix && bazel build ...  && cd /mynet/testbed/linux/multiplex/pthread && bazel build ...  && cd /mynet/testbed/linux/sysv && bazel build ...  && cd /mynet/hadoop && mvn package && cd /mynet/spark && mvn package && cd /mynet/testbed && mvn package
