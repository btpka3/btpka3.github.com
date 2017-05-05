#!/usr/bin/python3
import sys


class X:
    """一个简单的类实例"""
    i = 100

    # 两个下划线开头的属性是私有的
    __private_attr = 11

    # 构造函数
    def __init__(self):
        self.i += 1
        self._count = 99

    # 实例方法
    def f(self):  # self 参数为惯例名称，相当于 java 中的 this
        return 'hello world : ' + str(self.i)

    # 两个下划线开头的方法是私有的
    def __private_method(self):
        return ""

    # 静态方法
    @staticmethod
    def s():
        return "ss"

    @property
    def count(self):
        return self._count


x = X()  # 实例化

x.i += 2

print("----------------------------------- 实例")
print("x.i          = ", x.i)  # 实例变量
print("x.count      = ", x.count)  # count
print("x.f()        = ", x.f())  # 实例方法
print("x.__class__  = ", x.__class__)  # <class '__main__.X'>

print("----------------------------------- 类（静态）")
print("X.i      = ", X.i)
print("X.s()    = ", X.s())


# 支持多继承，比如： class X1(X,Y,Z):
# 查找类变量、方法时，先找自身，在从父类声明列表中依次从左向右查找

class X1(X):
    def f(self):  # self 参数为惯例名称，相当于 java 中的 this
        return 'hello world1 : ' + str(self.i)

    def getName(self):
        return "name-lalala"


x1 = X1()

print("----------------------------------- X1")
print("x1.i         = ", x1.i)  # 实例变量
print("x1.f()       = ", x1.f())  # 实例方法
print("x1.getName() = ", x1.getName())  # 实例变量


"""
类的专有方法(多用于运算符重载):
    __init__    : 构造函数，在生成对象时调用
    __del__     : 析构函数，释放对象时使用
    __repr__    : 打印，转换
    __setitem__ : 按照索引赋值
    __getitem__ : 按照索引获取值
    __len__     : 获得长度
    __cmp__     : 比较运算
    __call__    : 函数调用
    __add__     : 加运算
    __sub__     : 减运算
    __mul__     : 乘运算
    __div__     : 除运算
    __mod__     : 求余运算
    __pow__     : 称方

"""