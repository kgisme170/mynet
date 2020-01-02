# Base build image
FROM ubuntu-18.04 AS build_base

# Install some dependencies needed to build the project
RUN apt-get install -y --fix-missing ca-certificates git gcc g++ libc-dev

# Force the go compiler to use modules
ENV GO111MODULE=on
