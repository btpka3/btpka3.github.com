from urllib.parse import urlparse

import urllib

"""
https://docs.python.org/3/library/urllib.parse.html#module-urllib.parse
"""

url = 'https://www.baidu.com/111/222;jsessionid=xxxx?a=aa&b=b1&b=b2#/xxx/yyy?c=cc&d=dd'
o = urlparse(url)
print(o)
print(o.__class__)

print("============================ ")
scheme, netloc, path, params, query, fragment = urlparse(url)
print(scheme)
print(scheme.__class__)

u = urllib.parse.urlunparse(
    (

        "https",  # scheme
        "www.baidu.com", # netloc
        "/111/222", # path
        "jsessionid=xxxx" ,#params
        "a=aa&b=b1&b=b2",#query
        "/xxx/yyy?c=cc&d=dd"#fragment
    )
)
print(u)

# scheme, netloc, url, params, query, fragment
