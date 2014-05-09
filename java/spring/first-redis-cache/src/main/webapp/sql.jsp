<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ page import="java.util.*, java.util.regex.*, java.text.*, com.jamonapi.*, com.jamonapi.proxy.*, com.jamonapi.utils.*" %>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="userUrl" value="/ws/user" />
<html>
<body>
<div>
===========MonProxyFactory.getSQLDetailHeader()
<%
String[] titles = MonProxyFactory.getSQLDetailHeader();
for (int i = 0; i < titles.length; i++) {
    String title = titles[i];
    out.write("<div>");
    out.write(Integer.toString(i));
    out.write(". ");
    out.write(title);
    out.write("</div>");
} %>
</div>
<div>
===========MonProxyFactory.getSQLDetail()
<%
    Object[][] objArr = MonProxyFactory.getSQLDetail();
    if (objArr != null) {
        for (Object[] objs : MonProxyFactory.getSQLDetail()) {
            out.write("<div>~~~~~~~~</div>");
            for (int i = 0; i < objs.length; i++) {
                Object obj = objs[i];
                out.write("<div>");
                out.write(Integer.toString(i));
                out.write(". ");
                out.write(obj == null ? "null" : obj.toString());
                out.write("</div>");
            }
        }
    }
%>
</div>


</body>
</html>

