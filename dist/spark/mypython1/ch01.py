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

lines = sc.parallelize(["hello world", "hi"])
words = lines.flatMap(lambda line:line.split(" "))
print words.first()

data = sc.parallelize([1,2,3,4,1,3])
print data.reduce(lambda x,y: x+y)

def printall(rdd):
    for r in rdd:
        print r
printall(data.distinct().take(10))