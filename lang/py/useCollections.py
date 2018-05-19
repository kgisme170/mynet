from collections import namedtuple,deque,defaultdict,OrderedDict,Counter
p=namedtuple('Point',['x','y'])
print p
obj=p(x=1,y=2)
isinstance(obj,p)

class My:
    pass
m=My
obj2=m()
print obj2

q=deque()
q.append('a')
q.appendleft('b')
d=defaultdict(lambda:'N/A')
d['k1']=3
print d['k1'],d['k2']
c=Counter()
for e in ['good','bad','bad','bad','good']:
    c[e]=c[e]+1
print c