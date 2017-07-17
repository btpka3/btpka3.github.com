package me.test.first.chanpay.api.impl;

import org.apache.commons.io.*;
import org.springframework.http.*;
import org.springframework.http.client.*;
import org.springframework.web.client.*;

import java.io.*;
import java.nio.charset.*;

/**
 * 畅捷支付返回响应的 Content-Type 是 "text/html;charset=UTF-8", 需要修正为 JSON。
 */
public class ChanpayResponseErrorHandler implements ResponseErrorHandler {


    private ResponseErrorHandler delegate;

    @Override
    public boolean hasError(ClientHttpResponse response) throws IOException {

        HttpHeaders respHeaders = response.getHeaders();
        if (!MediaType.APPLICATION_JSON.equals(respHeaders.getContentType())) {
            return true;
        }
        if (delegate != null) {
            return delegate.hasError(response);
        }
        return false;

    }

    @Override
    public void handleError(ClientHttpResponse response) throws IOException {

//        new PushbackInputStream(response.getBody());

//        String str = IOUtils.toString(response.getBody(), StandardCharsets.UTF_8);
//        System.out.println("======================");
//        System.out.println(str);

        HttpHeaders respHeaders = response.getHeaders();
        if (!MediaType.APPLICATION_JSON.equals(respHeaders.getContentType())) {
            respHeaders.setContentType(MediaType.APPLICATION_JSON);
        }

        if (delegate != null && delegate.hasError(response)) {
            delegate.handleError(response);
        }
    }

    public ResponseErrorHandler getDelegate() {
        return delegate;
    }

    public void setDelegate(ResponseErrorHandler delegate) {
        this.delegate = delegate;
    }
}
