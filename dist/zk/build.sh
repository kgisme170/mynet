#!/bin/bash

gcc zk01.c -DTHREAD -I/usr/local/include/zookeeper/ /usr/local/lib/libzookeeper_mt.dylib -o zk01
