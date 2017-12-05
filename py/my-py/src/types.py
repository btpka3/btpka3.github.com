#!/usr/bin/python3

print("Hello, World!");
print("嗨, World!");

import sys;

x = 'runoob';
sys.stdout.write(x + '\n')
print(type(x), x.__class__)

aa = 100
print(aa)

print(0.1 + 0.2)  # 同样会有精度问题

print("----------------------------- bytes")

bbb = b'abc'
print(bbb, bbb.__len__(), bbb[0], bbb.hex(), bbb.decode("utf-8"))
bbb = bytes.fromhex('0902')
print(bbb, bbb.__len__(), bbb[0], bbb.hex(), bbb.decode("utf-8"))

print("----------------------------- string")
str = 'abcdefgh'

print(str)  # 输出完整字符串
print(str[0])  # 输出字符串中的第一个字符
print(str[2:5])  # 输出字符串中第三个至第五个之间的字符串
print(str[2:])  # 输出从第三个字符开始的字符串
print(str * 2)  # 输出字符串两次
print(str + "TEST")  # 输出连接的字符串

print("----------------------------- list")
list = ['aaa', 111, 222, 'bbb', 333]
list2 = [444, 'ccc']
print(list2.index("aaa"))

print(list)
print(list[1])
print(list + list2)
del list[1]
print(list)
print(222 in list)

for x in list:
    print("~~~~", x)

print("----------------------------- tuple")
# 元组的元素不能修改
tuple = ('aaa', 111, 222, 'bbb', 333)
tuple2 = (444, 'ccc')

print(tuple)
print(tuple[0])
print(tuple[2:5])
print(tuple[2:])
print(tuple * 2)
print(tuple + tuple2)

print("----------------------------- dictionary")
dict = {"a": "aaa", 1: "111", 3: [31, 32]}
dict['b'] = 'bbb'

dict2 = {2: [221, 222]}

print(dict)
print(dict['a'])
print(dict.keys())
print(dict.values())
