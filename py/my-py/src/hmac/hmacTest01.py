import hmac
import hashlib
import base64

"""
https://mattyboy.net/code/hmac-sha1-java-python-and-php/
"""
data = "aaa"
key = "bbb"

h = hmac.new(key.encode("UTF-8"), data.encode('UTF-8'), hashlib.sha1)
s = h.digest()
b = s.hex()
print("b =", b)
