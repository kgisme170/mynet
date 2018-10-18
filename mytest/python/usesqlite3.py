#!/usr/bin/env python
# -*- coding: utf-8 -*-
import sqlite3
from pprint import pprint
conn=sqlite3.connect('d:\\mytestdb')
cursor=conn.cursor()
cursor.execute('create table if not exists user(id varchar(20) primary key, name varchar(20))')
cursor.execute('insert into user (id,name) values (\'2\',\'Michael\')')
cursor.rowcount
cursor.close()
conn.commit()
cursor=conn.cursor()
cursor.execute('select * from user')
values=cursor.fetchall()
print values
cursor.close()
conn.close()
from pprint import pprint
data = (  
    "this is a string", [1, 2, 3, 4], ("more tuples",  
    1.0, 2.3, 4.5), "this is yet another string"  
    )
pprint(data)