#!/bin/bash
source /etc/environment
# ant
cd /mynet/testbed/build/useAnt && ant
# bazel
cd /mynet/testbed/build/useBazel/cpp-project
chmod +x ./run.sh
./run.sh
cd /mynet/testbed/build/useBazel/java-project
chmod +x ./run.sh
./run.sh

# cmake
cd /mynet/testbed/build/useCmake && cmake . && make -j8

# gradle
cd /mynet/testbed/build/useGradle && gradle

# maven
cd /mynet/testbed/build/useMaven && mvn package

# ninja
cd /mynet/testbed/build/useNinja && ninja

# sbt

# scons
cd /mynet/testbed/build/useScons && scons -j8
cd /mynet/testbed/build/useScons/testStaticInfo && scons -j8

# swig
cd /mynet/testbed/build/useSwig && scons -j8

# cpp
cd /mynet/testbed/cpp && chmod +x testgmock.sh && scons -j8

# cpp3p
cd /mynet/testbed/cpp3p && chmod +x install.sh && ./install.sh

# linux
cd /mynet/testbed/linux/multiplex/linux && bazel build ...
cd /mynet/testbed/linux/multiplex/posix && bazel build ...
cd /mynet/testbed/linux/multiplex/pthread && bazel build ...
cd /mynet/testbed/linux/sysv && bazel build ...

# java
cd /mynet/testbed && mvn package
