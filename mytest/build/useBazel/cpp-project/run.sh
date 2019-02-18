#!/bin/bash
bazel build hello-greet
bazel build hello-dl
bazel-bin/hello-dl
