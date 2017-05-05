#!/usr/bin/python3
import sys

# 使用了 yield 的函数被称为生成器（generator）
# 生成器是一个返回迭代器的函数，只能用于迭代操作

def fibonacci(n):  # 生成器函数 - 斐波那契
    a, b, counter = 0, 1, 0
    while True:
        if (counter > n):
            return
        yield a
        a, b = b, a + b
        counter += 1


f = fibonacci(10)  # f 是一个迭代器，由生成器返回生成

while True:
    try:
        print(next(f), end=" ")
    except StopIteration:
        sys.exit()
