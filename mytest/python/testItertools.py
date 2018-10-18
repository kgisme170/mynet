#!/usr/bin/env python
# -*- coding: utf-8 -*-
import itertools
for e in itertools.repeat('a',8):
    print e

odds=itertools.count(1,2)
for o in itertools.takewhile(lambda x:x<20,odds):
    print o

for c in itertools.chain(['abc','xyz'],[12,34]):
    print c

for k, group in itertools.groupby('AaaBaabBCacA', lambda c:c.upper()):
    print k,group,list(group)

for x in itertools.imap(lambda x:x*x, [1,2,3,4]):
    print x