#!/bin/bash
source /etc/environment
cd /mynet/testbed/build/useAnt && ant && cd /mynet/testbed/build/useBazel/cpp-project && bazel build ... && cd /mynet/testbed/build/useBazel/java-project && bazel build ... && cd /mynet/testbed/build/useCmake && mkdir build && cd build && cmake .. && make -j8 && cd /mynet/testbed/build/useGradle && gradle && cd /mynet/testbed/build/useMaven && mvn package && cd /mynet/testbed/build/useNinja && ninja && cd /mynet/testbed/build/useScons && scons -j8 && cd /mynet/testbed/build/useScons/testStaticInfo && scons -j8 && cd /mynet/testbed/build/useSwig && scons -j8 && cd /mynet/testbed/cpp && scons -j8 && cd /mynet/testbed/cpp3p && ./build.sh && cd /mynet/testbed/linux/multiplex/linux && bazel build ... && cd /mynet/testbed/linux/multiplex/posix && bazel build ... && cd /mynet/testbed/linux/multiplex/pthread && bazel build ... && cd /mynet/testbed/linux/sysv && bazel build ... && cd /mynet/testbed/linux/module && make -j8 && cd /mynet/testbed/go/cgo && ./build.sh && cd /mynet/testbed && mvn package && cd /mynet/hadoop/ && mvn package && cd /mynet/spark/ && mvn package && cd /mynet/testbed/csharp/NUnitTestProject_core && dotnet build
