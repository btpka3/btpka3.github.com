import email.utils
import time
from calendar import timegm
from datetime import datetime


class Date:

    @classmethod
    def str2time(cls, date):
        return Date.epoch2time(Date.str2epoch(date))

    @classmethod
    def str2epoch(cls, date):
        return int(timegm(email.utils.parsedate_tz(date)))

    @classmethod
    def time2str(cls, dt):
        if isinstance(dt, datetime) is False:
            raise Exception("date is not a datetime object")

        weekday = [
            "Mon", "Tue", "Wed", "Thu", "Fri", "Sat", "Sun"
        ][dt.weekday()]

        month = ["Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep",
             "Oct", "Nov", "Dec"][dt.month - 1]

        return "{0}, {1:02d} {2} {3} {4}:{5:02d}:{6:02d} GMT".format(
            weekday, dt.day, month, dt.year, dt.hour, dt.minute, dt.second)

    @classmethod
    def time2epoch(cls, date):
        return date.strftime('%s')

    @classmethod
    def epoch2time(cls, epoch):
        return datetime.utcfromtimestamp(epoch)

    @classmethod
    def epoch2str(cls, epoch):
        return Date.time2str(Date.epoch2time(epoch))
