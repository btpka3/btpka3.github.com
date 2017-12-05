import base64


def bytes_2_base64():
    print("===================== bytes_2_base64")
    b = b"aaa"
    base64_str = base64.b64encode(b)
    print("bytes     : ", b)
    print("base64Str : ", base64_str)


bytes_2_base64()


def base64_2_bytes():
    print("===================== base64_2_bytes")
    base64_str = "YWE="
    b = base64.b64decode(base64_str)
    print("base64Str : ", base64_str)
    print("bytes     : ", b)


base64_2_bytes()


def hex_2_bytes():
    print("===================== hex_2_bytes")
    hex_str = '0902'
    b = bytes.fromhex('0902')
    print("hex str : ", hex_str)
    print("bytes   : ", b)


hex_2_bytes()


def bytes_2_hex():
    print("===================== bytes_2_hex")
    b = b'abc'
    hex_str = b.hex()
    print("bytes   : ", b)
    print("hex_str : ", hex_str)


bytes_2_hex()


def str_2_bytes():
    print("===================== str_2_bytes")
    s = "abc"
    # b=bytearray(s,"utf-8")
    b = s.encode("utf-8")
    print("str   : ", s)
    print("bytes : ", b)


str_2_bytes()


def bytes_2_str():
    print("===================== bytes_2_str")
    b = b'\x55\x56\x57'
    s = b.decode("utf-8")
    print("bytes : ", b)
    print("str   : ", s)


bytes_2_str()

from urllib import parse


def urlencode01():
    print("===================== urlencode01")
    data = {
        "a": "aa",
        "b": "b b",
        "c": "中",
        "d": "A-Za-z0-9-_.~"
    }
    print("---1")
    print( parse.urlencode(data))
    print("---")


urlencode01()
