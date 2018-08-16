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
    print("----------")
    for r in list(rdd.collect()):
        print r
printall(data.distinct())
for d in list(data.distinct().collect()):
    print d

d = sc.parallelize(["1, hello", "2, hi", "3, how are you"])
for _ in list(d.map(lambda x:(x.split(",")[0], x)).collect()):
    print _

n = sc.parallelize([1,3,4,2,1])
sums = n.aggregate((0,0),
    (lambda p, value:(p[0]+value, p[1]+1)),
    (lambda p1, p2:(p1[0]+p2[0], p1[1]+p2[1])))
print sums[0]/float(sums[1])
rdd1 = sc.parallelize([1,2,3])
rdd2 = sc.parallelize([3,4,5])
u = rdd1.union(rdd2)
printall(u)
printall(rdd1.intersection(rdd2))
printall(rdd1.subtract(rdd2))
printall(rdd1.cartesian(rdd2))