import re
#print re.match("?([0-9]{2})*","a123456").group()
print re.match("([0-9]{2})*","123456").groups()
print re.match("([0-9]{2})?","123456").group()
#print re.match("\bThe\b","in The word").group()
print re.match(r"(?i)foo","Foo").group()
print re.search(r'\bthe', 'bite the dog').group()
print re.findall('car', 'Carry the bar to the car', re.I)
for m in re.finditer('car', 'Carry the bar to the car', re.I):
    print m.group(0)

s='This and that.'
print re.finditer(r'(th\w+) and (th\w+)',s,re.I).next().groups()
print re.finditer(r'th',s,re.I).next().groups()
print re.findall(r'th',s,re.I)