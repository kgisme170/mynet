from types import MethodType
class My(object):
#    __slots__=('name','age')
    def f1(self):
        print 'f1'

    @property
    def score(self):
        return self._score
    @score.setter
    def score(self,value):
        if value>100:
            value=100
        self._score=value
    @property
    def birth(self):
        return self._birth
    @birth.setter
    def birth(self,value):
        self._birth=value

    @property
    def age(self):
        return 2018-self._birth
def f2(self):
    print 'f2'
def f3(self):
    print 'f2'

m=My()
m.f2=MethodType(f2,m)
m.f2()

My.f3=f3
n=My()
n.f3()
n.gender='male'
print n.gender
n.score=200
print n.score
n.birth=1980
print n.age

from enum import Enum

Month = Enum('Month', ('Jan', 'Feb', 'Mar', 'Apr', 'May', 'Jun', 'Jul', 'Aug', 'Sep', 'Oct', 'Nov', 'Dec'))
for name, member in Month.__members__.items():
    print(name, '=>', member, ',', member.value)
print type(Month)
import doctest
class Dict(dict):
    '''
    Simple dict but also support access as x.y style.

    >>> d1 = Dict()
    >>> d1['x'] = 100
    >>> d1.x
    100
    >>> d1.y = 200
    >>> d1['y']
    200
    >>> d2 = Dict(a=1, b=2, c='3')
    >>> d2.c
    '3'
    >>> d2['empty']
    Traceback (most recent call last):
        ...
    KeyError: 'empty'
    >>> d2.empty
    Traceback (most recent call last):
        ...
    AttributeError: 'Dict' object has no attribute 'empty'
    '''
    def __init__(self, **kw):
        super(Dict, self).__init__(**kw)

    def __getattr__(self, key):
        try:
            return self[key]
        except KeyError:
            raise AttributeError(r"'Dict' object has no attribute '%s'" % key)

    def __setattr__(self, key, value):
        self[key] = value
doctest.testmod()
