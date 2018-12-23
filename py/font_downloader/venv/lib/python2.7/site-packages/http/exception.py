def http_exception(response):
    raise HTTPException(response)


class HTTPException(Exception):

    def __init__(self, response):
        self._response = response

    def __str__(self):
        return self._response.message

    @property
    def is_redirect(self):
        return self._response.is_redirect

    @property
    def is_client_error(self):
        return self._response.is_client_error

    @property
    def is_server_error(self):
        return self._response.is_server_error
