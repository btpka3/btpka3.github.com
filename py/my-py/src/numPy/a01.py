#!/usr/bin/python3

"""
# dimension (维度) 称之为 axis (轴)(最外围的维度的长度)
# axis (轴) 的长度称之为 rank (阶层)(内层元素长度)

# axis == 1,  rank == 1 (第二层维度的元素是单个数值，故单个数值的长度为1)
[1, 2, 1]

# axis == 2 (最外围的维度的长度),  rank == 3  (内层元素长度)
[[ 1., 0., 0.],
 [ 0., 1., 2.]]

"""

import numpy as np

"""
[ 0  1  2  3  4  5  6  7  8  9 10 11 12 13 14]
"""
print(np.arange(15))

"""
[[ 0  1  2  3  4]
 [ 5  6  7  8  9]
 [10 11 12 13 14]]
"""
a = np.arange(15).reshape(3, 5)
print(a)

print("a.__class__      = ", a.__class__)  # "<class 'numpy.ndarray'>"
print("a.ndim           = ", a.ndim)  # axis
print("a.shape          = ", a.shape)
print("a.size           = ", a.size)  # 总元素数
print("a.dtype          = ", a.dtype)  # 元素数据类型
print("a.dtype.name     = ", a.dtype.name)  # int64
print("a.itemsize       = ", a.itemsize)  # 元素的字节数
print("a.data           = ", a.data)

print("np.zeros((3,4))  = \n", np.zeros((3, 4)))
print("np.zeros((2,3))  = \n", np.ones((2, 3)))
print("np.empty((2,3))  = \n", np.empty((2, 3)))  # 未初始化，内容随机

# 按固定步长在指定范围内生成数列
print("np.arange(10, 30, 5)  = \n", np.arange(10, 32, 5))

# 在给定数值区间，生成等差数列
print("np.linspace(0, 2, 9)  = \n", np.linspace(0, 2, 9))

# 变为一维数组
print("np.arange(6) = \n", np.arange(6))
# 变为二维数组
print("np.arange(12).reshape(4,3)  = \n", np.arange(12).reshape(4, 3))
# 变为三维数组
print("np.arange(24).reshape(2,3,4))  = \n", np.arange(24).reshape(2,3,4))

