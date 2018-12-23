from date import Date
from datetime import datetime


class Headers(object):
    """
    Class to manipulate HTTP headers
    """

    def __init__(self, headers=None):
        """
        Construct a new ``Header`` object

        :param headers: headers
        :type headers: a list or a dict
        """
        if headers is None:
            headers = []

        if isinstance(headers, dict):
            _headers = []
            for k in headers:
                _headers.append((k, headers[k]))
            headers = _headers

        self._headers = headers

    def __getitem__(self, key):
        if isinstance(key, (int, long)):
            return self._headers[key][0]
        for k, v in self._headers:
            if k.lower() == key.lower():
                return v
        return None

    def __delitem__(self, key):
        key = key.lower()
        new = []
        for k, v in self._headers:
            if k.lower() != key:
                new.append((k, v))
        self._headers[:] = new

    def __str__(self):
        strs = []
        for key, value in self.to_list():
            strs.append('%s: %s' % (key, value))
        strs.append('\r\n')
        return '\r\n'.join(strs)

    def items(self):
        """
        Returns a list of items

        :rtype: list
        """
        return list(self.iteritems())

    def iteritems(self):
        """
        Returns an iterator

        :rtype: iterator
        """
        for k, v in self._headers:
            yield k, v

    def to_list(self):
        return [(k, str(v)) for k, v in self._headers]

    def add(self, key, *values):
        """
        Adds a new header and one or multiple values

        :param key: name of the header
        :type key: string
        :param \*values: one or many values for this header
        """
        for value in values:
            self._headers.append((key, value))

    def get(self, key):
        """
        Returns the value for a given header

        :param key: name of the header
        :type key: string
        :rtype: string
        """
        value = self.__getitem__(key)
        if isinstance(value, datetime):
            value = Date.time2str(value)
        return value

    def get_all(self, key):
        """
        Returns all the values for a given header

        :param key: name of the header
        :type key: string
        :rtype: list
        """
        return self.get_list(key)

    def get_list(self, key):
        values = []
        for header_values in self._headers:
            values.append(header_values[1])
        return values

    def set(self, key, value):
        """
        Set a header to some specific value. If this header already exists,
        and there is more than one value for this header, the new value
        replace the first one

        :param key: name of the header
        :type key: string
        :param value: new value
        """
        if not self._headers:
            self._headers.append((key, value))
            return

        lkey = key.lower()
        for idx, (prev_key, prev_value) in enumerate(self.iteritems()):
            if prev_key.lower() == lkey:
                self._headers[idx] = (key, value)
                break
        self._headers.append([key, value])

    def remove(self, key):
        """
        Removes a header

        :param key: name of the header
        :type key: string
        """
        self.__delitem__(key)

    @property
    def content_type(self):
        """
        Returns the value for the *Content-Type* header

        :rtype: string
        """
        ct = self.get('Content-Type')
        if ct:
            # Return only the type, scrubbing type parameters
            return ct.split(';', 1)[0]
        else:
            return None

    @property
    def content_type_params(self):
        """
        Returns the type parameters for the *Content-Type* header

        :rtype: dictionary
        """
        type_str = self.get('Content-Type').split(';', 1)
        if len(type_str) == 1:
            return {}
        else:
            params = {}
            for param in type_str[1].split(';'):
                k, v = map(str.strip, param.split('='))
                params[k] = v
            return params

    @property
    def content_length(self):
        """
        Returns the value for the *Content-Length* header

        :rtype: int
        """
        return int(self.get('Content-Length'))

    @property
    def content_is_json(self):
        """
        Returns True if the "Content-Type" header is set to application/json

        :rtype: boolean
        """
        ct = self.content_type
        if ct is None:
            return False
        if ct == 'application/json':
            return True
        return False

    @property
    def content_is_text(self):
        """
        Returns True if the "Content-Type" header is set to text

        :rtype: boolean
        """
        ct = self.content_type
        if ct is None:
            return False
        if ct.startswith('text/'):
            return True
        return False

    @property
    def content_is_xhtml(self):
        """
        Returns True if the "Content-Type" header is set to *xhtml*

        :rtype: boolean
        """
        ct = self.content_type
        if ct is None:
            return False
        if ct == 'application/xhtml+xml':
            return True
        if ct == 'application/vnd.wap.xhtml+xml':
            return True
        return False

    @property
    def content_is_xml(self):
        """
        Returns True if the "Content-Type" header is set to *xml*

        :rtype: boolean
        """
        ct = self.content_type
        if ct is None:
            return False
        if ct == 'text/xml':
            return True
        if ct == 'application/xml':
            return True
        if ct.endswith('+xml'):
            return True
        return False

    @property
    def last_modified(self):
        """
        Returns a datetime object represeting the *Last-Modified* header

        :rtype: datetime
        """
        return self._get_date_header('Last-Modified')

    @last_modified.setter
    def last_modified(self, date):
        """Set the value of the *Last-Modified* header"""
        return self._set_date_header('Last-Modified', date)

    @property
    def date(self):
        """
        Returns a datetime object represeting the *Date* header

        :rtype: datetime
        """
        return self._get_date_header('Date')

    @date.setter
    def date(self, date):
        """Set the value of the *Date* header"""
        return self._set_date_header('Date', date)

    @property
    def expires(self):
        """
        Returns a datetime object represeting the *Expires* header

        :rtype: datetime
        """
        return self._get_date_header('Expires')

    @expires.setter
    def expires(self, date):
        """Set the value of the *Expires* header"""
        return self._set_date_header('Expires', date)

    @property
    def if_modified_since(self):
        """
        Returns a datetime object represeting the *If-Modified-Since* header

        :rtype: datetime
        """

        return self._get_date_header('If-Modified-Since')

    @if_modified_since.setter
    def if_modified_since(self, date):
        """Set the value of the *If-Modified-Since* header"""
        return self._set_date_header('If-Modified-Since', date)

    @property
    def if_unmodified_since(self):
        """
        Returns a datetime object represeting the *If-Unmodified-Since* header

        :rtype: datetime
        """

        return self._get_date_header('If-Unmodified-Since')

    @if_unmodified_since.setter
    def if_unmodified_since(self, date):
        """Set the value of the *If-Unmodified-Since* header"""
        return self._set_date_header('If-Unmodified-Since', date)

    def _get_date_header(self, key):
        value = self.__getitem__(key)
        if value is None:
            return None

        if isinstance(value, str):
            return Date.str2time(value)
        elif isinstance(value, int):
            return Date.epoch2time(value)
        elif isinstance(value, datetime):
            return value
        else:
            print value
            raise ValueError("date is of type <{type}> but can only be an "
                "instance of string, int or a datetime object"
                .format(type=type(value)))

    def _set_date_header(self, key, date):
        # XXX for now we only document that the helpers can accept a
        # datetime object, but you can also pass a string and a int.
        # Let's see in the futur if this is usefull and document all
        # the behavior
        if isinstance(date, datetime):
            date = date
        elif isinstance(date, int):
            date = Date.epoch2time(date)
        elif isinstance(date, str):
            date = Date.str2time(date)
        else:
            raise ValueError("date is of type <{type}> but can only be an "
                "instance of string, int or a datetime object"
                .format(type=type(date)))
        self.set(key, date)
