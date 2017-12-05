import collections


def t1():
    print("====================== t1")
    d = {2: 3, 1: 89, 4: 5, 3: 0}
    print(d.items())
    od = collections.OrderedDict(sorted(d.items()))
    print(od)

    for k, v in d.items():
        print("k = " + str(k) + ", v = " + str(v))


t1()


def t2():
    print("====================== t2")
    d = {
        "a2": "a2",
        "c2": "c2",
        "a1": "a1",
        "c": "c1"
    }
    print(d.items())
    od = collections.OrderedDict(sorted(d.items()))
    print(od)


t2()


def t3():
    print("====================== t3")
    a1, a2 = {  # 注意顺序
        "a2": "a2",
        "a1": "a1"
    }
    print("a1 = ", a1)
    print("a2 = ", a2)


t3()


def t4():
    print("====================== t4")
    a = {
        "a2": "a2",
        "a1": "a1"
    }
    a1, a2 = a
    print("a1 = ", a1)
    print("a2 = ", a2)


t4()
