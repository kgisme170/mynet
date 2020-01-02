#!/bin/bash
bazel query '//...'|xargs bazel build
