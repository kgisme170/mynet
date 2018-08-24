import pytest_pb2
import sys
person=pytest_pb2.Person()
person.name="bbb"
person.id=9

phone_number1=person.phone.add()
phone_number1.number="aaa"
phone_number2=person.phone.add()
phone_number2.number="ccc"
f=open("log4py.data","w")
s=person.SerializeToString()
f.write(s)
f.close()
