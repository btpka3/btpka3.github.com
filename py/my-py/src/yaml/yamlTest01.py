"""
pip3 install PyYAML 
"""
import sys
import yaml

document = """
  a: 1
  b:
    c: 3
    d: 4
"""
l = yaml.load(document)


assert l and type(l)==dict, "~@@@"

print("-------------------- l : ", l, type(l), l['a'])

if l.get('xx'):
    print("-------aaa")
else:
    print("-------bbb")

d = yaml.dump(l)
print("-------------------- d : ", d, type(d))

print({
    "x": "xxx",
    "y": "yyy"
})
