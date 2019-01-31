#!/bin/bash
mkdir -p build
thrift -r --gen cpp -o build worker.thrift
thrift -r --gen cpp -o build student.thrift
