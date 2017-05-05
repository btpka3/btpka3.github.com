#!/usr/bin/python3

# 操作系统接口
print("---------------------------------操作系统接口")

import os

print("os.getcwd() = ", os.getcwd())

print("---------------------------------文件通配符")
import glob

print("glob.glob('*.py') = ", glob.glob('*.py'))

print("---------------------------------命令行参数")
import sys

print("sys.argv = ", sys.argv)
sys.stderr.write('Warning, log file not found starting a new one\n')

print("---------------------------------正则表达式")
import re

print(re.sub(r'(\b[a-z]+) \1', r'\1', 'cat in the the hat'))

print("---------------------------------数学")
import math

print("math.pi                  = ", math.pi)
print("math.cos(math.pi / 4)    = ", math.cos(math.pi / 4))

print("---------------------------------随机数")

import random

print("random.choice(...)   = ", random.choice(['AAA', 'BBB', 'CCC']))
print("random.sample(...)   = ", random.sample(range(100), 10))
print("random.random()      = ", random.random())

print("---------------------------------访问 互联网")
from urllib.request import urlopen

for line in urlopen('http://tycho.usno.navy.mil/cgi-bin/timer.pl'):
    line = line.decode('utf-8')
    if 'EST' in line or 'EDT' in line:
        print(line)

# import smtplib
# server = smtplib.SMTP('localhost')
# server.sendmail('from@mail.org', 'to@mail.org',
# """
# To: zhang3@example.org
# From: li4@example.org
# Hello World.
# """)
# server.quit()


print("---------------------------------日期和时间")
from datetime import date
now = date.today()
print("now = ", now)
print("now.strftime(...) = ", now.strftime("%Y-%m-%d"))


