import time
import datetime

print(time.time())

localtime = time.localtime(time.time())

print("本地时间0为 :", localtime)

localtime = time.asctime(time.localtime(time.time()))
print("本地时间1为 :", localtime)

# 格式化
print(time.strftime("%Y-%m-%d %H:%M:%S", time.localtime()))

# YYYY-MM-DDThh:mm:ssZ
print("========")
#UTC_FORMAT = "%Y-%m-%dT%H:%M:%S.%fZ"
UTC_FORMAT = "%Y-%m-%dT%H:%M:%SZ"
print(time.strftime(UTC_FORMAT, time.localtime()))
utc_datetime = datetime.datetime.utcnow()
print(utc_datetime.strftime(UTC_FORMAT))

# 解析
utc = "2014-09-18T10:42:16.126Z"
local = "2014-09-18 10:42:16"
UTC_FORMAT = "%Y-%m-%dT%H:%M:%S.%fZ"
LOCAL_FORMAT = "%Y-%m-%d %H:%M:%S"
print("解析出来的 UTC 时间为：", datetime.datetime.strptime(utc, UTC_FORMAT))
print("解析出来的LOCAL时间为：", datetime.datetime.strptime(local, LOCAL_FORMAT))


# def utc2local(utc_st):
#     now_stamp = time.time()
#     local_time = datetime.datetime.fromtimestamp(now_stamp)
#     utc_time = datetime.datetime.utcfromtimestamp(now_stamp)
#     offset = local_time - utc_time
#     local_st = utc_st + offset
#     return local_st
#
# def local2utc(local_st):
#     time_struct = time.mktime(local_st.timetuple())
#     utc_st = datetime.datetime.utcfromtimestamp(time_struct)
#     return utc_st
#
#
# print(time.strftime(UTC_FORMAT,  time.localtime(time.time())).to('UTC'))