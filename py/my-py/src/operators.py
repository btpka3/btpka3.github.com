#!/usr/bin/python3

print("----------------------------- operators")
print(5 / 3)  # 1.6666666666666667
print(5 // 3)  # 1
print(5 % 3)  # 2
print(2 ** 3)  # 8

print("----------------------------- bit operators")
print(4 | 2)  # 8
print(~ 1)  # 按位取反: -2
print(4 ^ 5)  # 异或： 1

print("----------------------------- is operators")
tuple1 = {1, 2}
tuple2 = {1, 2}
tuple3 = tuple1

print(tuple1 is tuple2) # False
print(tuple1 is tuple3) # True
