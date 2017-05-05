#!/usr/bin/python3

print("----------------------------- if...elif...else...")
a = 3
if a == 1:
    print("a==1")
elif a == 2:
    print("a==2")
else:
    print("a!=1 && a!=2. a is " + str(a))
    print("aaaa")
print("end if")

print("----------------------------- while")

num = 3
i = 0
while i < 3:
    i += 1
    print("i=" + str(i))
else:
    print("end while1")
print("end while2")

print("----------------------------- for")

for letter in "abc":
    print("cur letter = " + letter)

list = ["aaa", 111, "ccc"]

for ele in list:
    print("cur element in list = ", ele)

for ele in list:
    print("cur element in iterator = ", ele)

print("----------------------------- function")


def hi(name):
    s = "hello " + str(name)
    return s


print(hi('zhang3'))
