# Base build image
FROM ubuntu:19.04 AS build_base

# Install some dependencies needed to build the project
RUN apt-get update \
    && apt-get -y upgrade \
    && apt-get install -y --fix-missing autoconf automake bison ca-certificates cmake curl flex git gcc golang graphviz g++ libc6-dev lua5.3 make openjdk-12-jdk openjdk-12-dbg openssl python ruby scons vim wget \
    && wget https://dl.google.com/go/go1.13.3.linux-amd64.tar.gz \
    && tar -xvf go1.13.3.linux-amd64.tar.gz \
    && mv go /usr/local \
    && export GOROOT=/usr/local/go \
    && mkdir goproj \
    && export GOPATH=$HOME/goproj \
    && export PATH=$GOPATH/bin:$GOROOT/bin:$PATH \
    && git clone https://github.com/kgisme170/mynet.git

# Force the go compiler to use modules
ENV GO111MODULE=on
