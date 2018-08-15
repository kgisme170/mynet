#!/usr/bin/env python
import findspark
findspark.init()
from pyspark import SparkContext
from pyspark import SparkConf
conf = SparkConf().setMaster("local").setAppName("My app")
sc = SparkContext(conf = conf)
lines = sc.textFile("ch01.py")
inputRDD = lines.filter(lambda x:"sc" in x)
for line in inputRDD.take(10):
    print line