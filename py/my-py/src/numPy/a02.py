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

x = np.array(['a', 'b'])
y = np.array([1, 2, 3])

print(x * y)
