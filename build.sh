#!/bin/bash

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
#

# scons
cd /mynet/testbed/build/useScons/doubleFree && scons -j8
cd /mynet/testbed/build/useScons/doubleLink && scons -j8
cd /mynet/testbed/build/useScons/libDependency && scons -j8
cd /mynet/testbed/build/useScons/testRemoveFile && scons -j8
cd /mynet/testbed/build/useScons/testStaticInfo && scons -j8
cd /mynet/testbed/build/useScons/useCpppath && scons -j8

# swig
cd /mynet/testbed/build/useSwig && scons -j8

# cpp
cd /mynet/testbed/cpp && chmod +x testgmock.sh && scons -j8

# cpp3p
