# -*- coding: utf-8 -*-
import urllib
from contextlib import contextmanager
class you(object):
    def __init__(self):
        print '__init__'
    def __str__(self):
        return 'you'
y=you()
print y

@contextmanager
def tag(name):
    print "begin ", name
    yield [1,2,3]
    print "end ", name

with tag("f1") as l:
    print "inside with: ", l

@contextmanager
def closing(thing):
    try:
        yield thing
    except (TypeError,ValueError) as err:
        print str(err)
    except BaseException as e:
        print e
    finally:
        thing.close()
#with suppress(FileNotFoundError):
#python3 才支持contextmanager.suppress  
with closing(urllib.urlopen('http://www.baidu.com')) as page:
    print page.read()