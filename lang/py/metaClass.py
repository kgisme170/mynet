#!/usr/bin/env python
# -*- coding: utf-8 -*-
def func(self,name="msg"):
    print "Func:", name
MyClass=type('MyClass',(object,),dict(myfunc=func))
m=MyClass()
m.myfunc("ok:")

class ListMetaClass(type):
    def __new__(cls, name, bases, attrs):
        attrs['add']=lambda self,value:self.append(value)
        return type.__new__(cls, name, bases, attrs)

class MyList(list):
    __metaclass__=ListMetaClass

l=MyList()
l.add(1)
l.add(2)
print l

class Field(object):
    def __init__(self, name, column_type):
        self.name = name
        self.column_type = column_type
    def __str__(self):
        return '<%s:%s>' % (self.__class__.__name__, self.name)

class StringField(Field):
    def __init__(self, name):
        super(StringField, self).__init__(name, 'varchar(100)')

class IntegerField(Field):
    def __init__(self, name):
        super(IntegerField, self).__init__(name, 'bigint')
class ModelMetaclass(type):
    def __new__(cls, name, bases, attrs):
        if name=='Model':
            return type.__new__(cls, name, bases, attrs)
        print('Found model: %s' % name)
        mappings = dict()
        for k, v in attrs.iteritems():
            if isinstance(v, Field):
                print('Found mapping: %s ==> %s' % (k, v))
                mappings[k] = v
        for k in mappings.iterkeys():
            attrs.pop(k)
        attrs['__mappings__'] = mappings # 保存属性和列的映射关系
        attrs['__table__'] = name # 假设表名和类名一致
        return type.__new__(cls, name, bases, attrs)

class Model(dict):
    __metaclass__ = ModelMetaclass

    def __init__(self, **kw):
        super(Model, self).__init__(**kw)

    def __getattr__(self, key):
        try:
            return self[key]
        except KeyError:
            raise AttributeError(r"'Model' object has no attribute '%s'" % key)

    def __setattr__(self,key,value):
        self[key]=value

    def save(self):
        field=[]
        param=[]
        args=[]
        for k,v in self.__mappings__.iteritems():
            field.append(v.name)
            param.append('?')
            args.append(getattr(self,k,None))
        sql='insert into %s (%s) values (%s)' % (self.__table__, ','.join(field), ','.join(param))
        print "SQL:", sql
        print "ARGS:", str(args)
class User(Model):
    id = IntegerField('id')
    name = StringField('username')
    email = StringField('email')
    password = StringField('password')

u = User(id=12345, name='Michael', email='test@orm.org', password='my-pwd')
u.save()