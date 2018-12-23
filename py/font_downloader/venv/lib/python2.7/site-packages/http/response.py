from headers import Headers
from url import Url


class Response(object):
    """
    The ``Response`` object encapsulates HTTP style responses.
    """

    def __init__(self, status, headers=Headers(), content=None,
        message=None, request=None):

        """
        Construct a new ``Response`` object.

        :param status: HTTP status code for the response
        :type status: integer
        :param headers: HTTP headers
        :type status: a list of tuples or a class:`Headers` object
        :param content: content
        :param message: HTTP message for the response
        :param request: origin Request object used
        :type request: class:`Request`

        .. attribute:: redirects

           List of redirections

        """

        self._status = status
        self.message = message
        self.redirects = list()

        if (not isinstance(headers, Headers)):
            headers = Headers(headers)

        self._headers = headers

        self._content = content
        self._request = request

    @property
    def status(self):
        """
        Returns the HTTP status

        :rtype: int
        """

        return int(self._status)

    @property
    def is_info(self):
        """
        Returns if the response was informational

        :rtype: boolean
        """
        if self.status >= 100 and self.status < 200:
            return True
        return False

    @property
    def is_success(self):
        """
        Returns if the response was success

        :rtypen: boolean
        """
        if self.status >= 200 and self.status < 300:
            return True
        return False

    @property
    def is_redirect(self):
        """
        Returns if the response was redirect

        :rtype: boolean
        """
        if self.status >= 300 and self.status < 400:
            return True
        return False

    @property
    def is_client_error(self):
        """
        Returns if the response was a client error

        :rtype: boolean
        """
        if self.status >= 400 and self.status < 500:
            return True
        return False

    @property
    def is_server_error(self):
        """
        Returns if the response was a client server

        :rtype: boolean
        """
        if self.status >= 500 and self.status < 600:
            return True

    @property
    def is_error(self):
        """
        Returns if the response was an error

        :rtype: boolean
        """
        if self.is_client_error or self.is_server_error:
            return True
        return False

    @property
    def base(self):
        """
        Returns the base URI for this response

        :rtype: class:`Url` or None
        """

        url = None
        if self.header('Content-Base'):
            url = self.header('Content-Base')
        if self.header('Content-Location'):
            url = self.header('Content-Location')
        if url is None and self.request:
            url = self.request.url

        if not url:
            return None

        if not isinstance(url, Url):
            url = Url(url)

        return url

    @property
    def request(self):
        """
        Returns the request object that caused that response

        :rtype: class:`Request`
        """
        return self._request

    @property
    def content(self):
        """
        Returns the actual content of the response

        :rtype: string
        """
        return self._content

    @content.setter
    def content(self, content):
        """
        Set the actual content of the response
        """
        self._content = content

    def header(self, name):
        """
        Returns the value for a given header

        :rtype: string
        """
        return self._headers.get(name)

    @property
    def headers(self):
        """
        Returns the class:`Headers` object

        :rtype: class:`Headers`
        """
        return self._headers

    @property
    def status_line(self):
        """
        Returns the string '<code> <message>'

        :rtype: string
        """
        return "{0} {1}".format(self.status, self.message)

    @property
    def last_modified(self):
        """
        Returns a datetime object represeting the *Last-Modified* header

        :rtype: class:`datetime`
        """
        return self._headers.last_modified

    @property
    def date(self):
        """
        Returns a datetime object represeting the *Date* header

        :rtype: class:`datetime`
        """
        return self._headers.date

    @property
    def expires(self):
        """
        Returns a datetime object represeting the *Expires* header

        :rtype: class:`datetime`
        """
        return self._headers.expires

    @property
    def content_length(self):
        """
        Returns the content-length of the actual response

        :rtype: int
        """
        return self._headers.content_length

    @property
    def content_is_text(self):
        """
        Returns ``True`` if the "Content-Type" header is set to text

        :rtype: boolean
        """
        return self._headers.content_is_text

    @property
    def content_is_xml(self):
        """
        Returns ``True`` if the "Content-Type" header is set to XML

        :rtype: boolean
        """
        return self._headers.content_is_xml

    @property
    def content_is_xhtml(self):
        """
        Returns True if the "Content-Type" header is set to XHTML

        :rtype: boolean
        """
        return self._headers.content_is_xhtml
