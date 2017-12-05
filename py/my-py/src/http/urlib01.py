import urllib.request
f = urllib.request.urlopen("https://www.baidu.com/?a=aa")
print(f.read())