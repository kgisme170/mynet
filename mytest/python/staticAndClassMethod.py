class base(object):
    X=1
    @staticmethod
    def s1():
        print 's1:',base.X
    @classmethod
    def c1(cls):
        print 'c1:',cls.X
class derived(base):
    X=2

d=derived()
d.s1()
d.c1()
