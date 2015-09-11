<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>session test</title>
<%
java.util.Enumeration  e= request.getParameterNames();
javax.servlet.http.HttpSession sess = request.getSession();
if(sess!=null){
  while(e.hasMoreElements()){
   String paramName = (String)e.nextElement();
   sess.setAttribute(paramName, request.getParameter(paramName));
  }
}

%>
</head>
<body>

<div>current session id = ${pageContext.session.id}</div>
<table>
<tr><th>attr</th><th>value</th></tr>
<c:forEach items="${pageContext.session.attributeNames}" var="attrName">
<tr><td>${fn:escapeXml(attrName)}</td><td>${fn:escapeXml(sessionScope[attrName]) }</td></tr>
</c:forEach>
</table>
<c:url var="url1" value="index.jsp" />
<div><a href="${url1}">第一次JSP展示时，&lt;c:url&gt;中内部链接URL 会 添加 ";jsessionid=xxxx"，你可以删除cookie后再点击该URL。</a></div>

<c:url var="url2" value="index.jsp?a=b" />
<div><a href="${url2}">第一次JSP展示时，&lt;c:url&gt;中内部链接URL即使有参数，也会在合适的位置会添加 ";jsessionid=xxxx"，你可以删除cookie后再点击该URL。</a></div>
<%--
<c:url var="url3" value="/index.jsp" />
<div><a href="${url3}">url 3</a></div>


<c:url var="url4" value="/index.jsp?c=d" />
<div><a href="${url4}">url 4</a></div>
 --%>

<c:url var="url5" value="http://localhost:10001/index.html?c=d" />
<div><a href="${url5}" onclick="return false;">第一次JSP展示时，&lt;c:url&gt;中绝对URL不会添加 ";jsessionid=xxxx"</a></div>

<%--
<c:url var="url6" value="http://www.baidu.com/#wd=b" />
<div><a href="${url6}">url 6</a></div>
 --%>


<%-- 
<div><a href="index.jsp?invalidSession=true">invalidSession</a></div>
<%
  if(request.getParameter("invalidSession")!=null){
      session.invalidate();
  }
%>
 --%>


</body>
</html>