def str_split():
    print("------------------ str_split")
    s = "a,b,c,,,"
    l = s.split(",")
    print("s : ", s)
    print("l : ", l)


str_split()


def str_lstrip():
    print("------------------ str_lstrip")
    s = " a b "
    t = s.lstrip()
    print("s : '" + s + "'")
    print("t : '" + t + "'")


str_lstrip()


def str_rstrip():
    print("------------------ str_rstrip")
    s = ",,a,,b,,"
    r = s.rstrip(',')
    print("s : '" + s + "'")
    print("r : '" + r + "'")


str_rstrip()


def str_index():
    print("------------------ str_index")
    s = "abcdef"
    i = s.index('c')
    print("s : '" + s + "'")
    print("i : ", i)


str_index()


def str_cmp():
    print("------------------ str_cmp")
    s1 = "abc"
    s2 = "adf"
    c = s1 > s2
    print("s1 : '" + s1 + "'")
    print("s2 : '" + s2 + "'")
    print("c : ", c)


str_cmp()


def str_upper():
    print("------------------ str_upper")
    s1 = "abcDEF"
    s2 = s1.upper()
    print("s1 : '" + s1 + "'")
    print("s2 : '" + s2 + "'")


str_upper()


def str_lower():
    print("------------------ str_lower")
    s1 = "abcDEF"
    s2 = s1.lower()
    print("s1 : '" + s1 + "'")
    print("s2 : '" + s2 + "'")


str_lower()


def str_replace():
    print("------------------ str_replace")
    s1 = "abcDEbcF"
    s2 = s1.replace("bc", ",")
    print("s1 : '" + s1 + "'")
    print("s2 : '" + s2 + "'")


str_replace()


def str_find():
    print("------------------ str_find")
    s1 = "aaa.test13.kingsilk.com.cn"
    i = s1.find(".")
    print(i)
    while i >= 0:
        s = s1[i + 1:]
        print("---- : ", s)
        i = s1.find(".", i + 1)


str_find()


def str_rfind():
    print("------------------ str_rfind")
    s1 = "aaa.test13.kingsilk.com.cn"
    i = s1.rfind(".")
    print(i)
    i = s1.rfind(".", 0, i)
    print(i)
    while i >= 0:
        s = s1[i + 1:]
        print("---- : ", s)
        i = s1.rfind(".", 0, i)


str_rfind()


print("===========")
s="kingsilk.com.cn"
s1 = "aaa.test13.kingsilk.com.cn"
sp_idx = s1.rfind(s)
print(sp_idx)
print(s1[0:sp_idx-1])
print(s1[sp_idx:])