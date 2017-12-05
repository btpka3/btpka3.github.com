# 定义函数
def hello():
    print("Happy Birthday to you!")


# 调用函数
hello()


def my_add(a: int, b: int) -> int:
    """
    计算两个数值的和

    :param a: 数值1
    :param b: 数值2
    :return: 和
    """
    return a + b


print(my_add(1, 2))
