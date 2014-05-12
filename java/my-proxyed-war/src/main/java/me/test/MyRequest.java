
package me.test;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

public class MyRequest extends HttpServletRequestWrapper {

    public MyRequest(HttpServletRequest request) {

        super(request);
    }

    @Override
    public String getScheme() {

        HttpServletRequest request = (HttpServletRequest) this.getRequest();
        String proto = request.getHeader("X-Forwarded-Proto");

        return proto == null || proto.length() == 0
                ? request.getScheme()
                : proto;
    }

    @Override
    public String getRemoteAddr() {

        HttpServletRequest request = (HttpServletRequest) this.getRequest();
        String forwardedFor = request.getHeader("X-Forwarded-For");
        if (forwardedFor == null || forwardedFor.length() == 0) {
            return request.getRemoteAddr();
        }
        String[] ips = forwardedFor.split(",");
        return ips.length == 0
                ? request.getRemoteAddr()
                : ips[0].trim();
    }

    @Override
    public String getRemoteHost() {

        return super.getRemoteAddr();
    }

    @Override
    public boolean isSecure() {

        return "https".equalsIgnoreCase(this.getScheme());
    }

    @Override
    public int getRemotePort() {

        HttpServletRequest request = (HttpServletRequest) this.getRequest();
        String forwardedFor = request.getHeader("X-Forwarded-For");
        return forwardedFor == null || forwardedFor.length() == 0
                ? super.getRemotePort()
                : -1;
    }

}
