import json

print(json.dumps(['foo', {'bar': ('baz', None, 1.0, 2)}]))

d = json.loads('["foo", {"bar":["baz", null, 1.0, 2]}]')
print(d, type(d), type(d[1]))
