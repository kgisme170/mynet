#!/bin/bash
#java -classpath bazel-bin/hello.jar com.demo.DemoRunner
bazel build //:hello_deploy.jar && java -jar bazel-bin/hello_deploy.jar
