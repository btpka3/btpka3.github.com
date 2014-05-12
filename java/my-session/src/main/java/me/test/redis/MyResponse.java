
package me.test.redis;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

public class MyResponse extends HttpServletResponseWrapper {

    public MyResponse(HttpServletResponse response) {

        super(response);
        // TODO Auto-generated constructor stub
    }

    public String encodeURL(String url) {

        return null; // TODO
    }

    public String encodeUrl(String url) {

        return encodeURL(url);
    }

    public String encodeRedirectURL(String url) {

        return null; // TODO
    }

    public String encodeRedirectUrl(String url) {

        return encodeRedirectURL(url);
    }

}
