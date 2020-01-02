# Base build image
FROM ubuntu:19.04 AS build_base

# Install some dependencies needed to build the project
RUN apt-get upgrade \
    && apt-get install -y --fix-missing autoconf automake bison ca-certificates cmake curl flex git gcc golang graphviz g++ libc6-dev lua5.3 make openssl python ruby scons wget

# Force the go compiler to use modules
ENV GO111MODULE=on
