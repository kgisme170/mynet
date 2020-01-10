# Base build image
FROM ubuntu:19.04 AS build_base

# Install some dependencies needed to build the project
RUN apt-get update \
    && apt-get -y upgrade \
    && apt-get install -y --fix-missing ant autoconf automake bison ca-certificates clang cmake curl flex git gcc gradle graphviz g++ libc6-dev lua5.3 llvm make maven ninja-build openjdk-8-jdk openjdk-8-jre openssl python python-dev ruby scons swig tcl tcl-dev vim wget \
    && wget https://dl.google.com/go/go1.13.3.linux-amd64.tar.gz \
    && tar -xvf go1.13.3.linux-amd64.tar.gz \
    && mv go /usr/local \
    && export GOROOT=/usr/local/go \
    && mkdir goproj \
    && export GOPATH=$HOME/goproj \
    && export PATH=$GOPATH/bin:$GOROOT/bin:$PATH \
    && git clone https://github.com/kgisme170/mynet.git \
    && echo "deb [arch=amd64] http://storage.googleapis.com/bazel-apt stable jdk1.8" | tee /etc/apt/sources.list.d/bazel.list \
    && curl https://bazel.build/bazel-release.pub.gpg | apt-key add - \
    && apt-get update && apt-get install bazel

# Force the go compiler to use modules
COPY environment /etc/environment
ENV GO111MODULE=on
