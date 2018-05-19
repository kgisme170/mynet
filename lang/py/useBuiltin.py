import hashlib
import base64
md5=hashlib.md5()
print md5
md5.update('hello world from me')
print md5.hexdigest()
md5.update('again')
print md5.hexdigest()

obj=hashlib.md5()
obj.update('again')
print obj.hexdigest()
obj2=hashlib.md5()
obj2.update('again')
print obj2.hexdigest()

print base64.urlsafe_b64encode("abcde")#why it still has '='?
print base64.b64encode("abcde")