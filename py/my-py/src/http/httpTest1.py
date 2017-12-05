import http.client

"""
urllib.request
https://docs.python.org/3/library/urllib.request.html#module-urllib.response

http.client
https://docs.python.org/3/library/http.client.html#httpresponse-objects

"""

headers = {'X-test': 'test1'}


conn = http.client.HTTPSConnection("www.baidu.com")

conn.request("GET", "/")
r1 = conn.getresponse()


print(r1.status, r1.reason,r1.msg, r1.read())
print(r1.__class__)
print(r1.__class__.__name__)