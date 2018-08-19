class m(object):
    def set_age(self,a):
        self._age=a
    def get_age(self):
        return self._age
    def __init__(self):
        self._age=3
    age=property(get_age,set_age)
o=m()
o.age=5
print o.age

class n(object):
    @property
    def age(self):
        return self._age
    @age.setter
    def age(self,value):
        self._age=value
    def __init__(self):
        self._age=4
o2=n()
o2.age=6
print o2.age