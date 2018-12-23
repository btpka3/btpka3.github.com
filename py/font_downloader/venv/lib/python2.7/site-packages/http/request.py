from headers import Headers
from url import Url


class Request(object):
    """
    The ``Request`` object encapsulates HTTP style requests
    """

    def __init__(self, method, url, headers=None, content=None):
        """
        Construct a new ``Request`` object

        :param method: HTTP method
        :type method: string
        :param url: URL of the request
        :type url: string or class:`Url`
        :param headers: Headers for the request
        :type headers: list of tuples or class:`Headers`
        :param content: body
        """
        self.method = method
        self.content = content

        if not isinstance(url, Url):
            url = Url(url)
        self.url = url

        if headers is None:
            headers = Headers()
        elif not isinstance(headers, Headers):
            headers = Headers(headers)

        self._headers = headers

    @property
    def method(self):
        """
        Returns the HTTP method

        :rtype: string
        """
        return self._method

    @method.setter
    def method(self, value):
        """Set the HTTP method"""
        self._method = str(value)

    def header(self, name, value=None):
        """Returns the value of the given header

        :rtype: string
        """
        if value is None:
            return self._headers.get(name)
        else:
            self._headers.add(name, value)

    @property
    def headers(self):
        """
        Returns the Headers used for the request

        :rtype: class:`Headers`
        """
        return self._headers

    @property
    def if_modified_since(self):
        """
        Returns a datetime object representing the value of the
        "If-Modified-Since" header

        :rtype: class:`datetime`
        """
        return self._headers.if_modified_since

    @if_modified_since.setter
    def if_modified_since(self, date):
        """Set the value of the "If-Modified-Since" header"""
        self._headers.if_modified_since = date

    @property
    def if_unmodified_since(self):
        """
        Returns a datetime object representing the value of the
        "If-Unmodified-Since" header

        :rtype: class:`datetime`
        """
        return self._headers.if_unmodified_since

    @if_unmodified_since.setter
    def if_unmodified_since(self, date):
        """Set the value of the "If-Unmodified-Since" header"""
        self._headers.if_unmodified_since = date
