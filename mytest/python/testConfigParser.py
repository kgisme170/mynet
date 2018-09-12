#!/usr/bin/python
# -*- coding: UTF-8 -*-
import ConfigParser;
cf = ConfigParser.ConfigParser()
cf.read("test.conf")

# 返回所有的section
s = cf.sections()
print 'section:', s
o = cf.options("db")
print 'options:', o
v = cf.items("db")
print 'db:', v
print '-'*60

#可以按照类型读取出来
db_host = cf.get("db", "db_host")
db_port = cf.getint("db", "db_port")
db_user = cf.get("db", "db_user")
db_pass = cf.get("db", "db_pass")

# 返回的是整型的
threads = cf.getint("concurrent", "thread")
processors = cf.getint("concurrent", "processor")
print "db_host:", db_host
print "db_port:", db_port
print "db_user:", db_user
print "db_pass:", db_pass
print "thread:", threads
print "processor:", processors

#修改一个值，再写回去
cf.set("db", "db_pass", "zhaowei")
cf.write(open("test.conf", "w"))

#添加一个section。（同样要写回）
cf.add_section('liuqing')
cf.set('liuqing', 'int', '15')
cf.set('liuqing', 'bool', 'true')
cf.set('liuqing', 'float', '3.1415')
cf.set('liuqing', 'baz', 'fun')
cf.set('liuqing', 'bar', 'Python')
cf.set('liuqing', 'foo', '%(bar)s is %(baz)s!')
cf.write(open("test.conf", "w"))

#移除section 或者option 。（只要进行了修改就要写回的哦）
cf.remove_option('liuqing','int')
cf.remove_section('liuqing')
cf.write(open("test.conf", "w"))