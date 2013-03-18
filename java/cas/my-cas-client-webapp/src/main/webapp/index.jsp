<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Public Page</title>
</head>
<body>
  <h1>Public Page</h1>
  <div>This is a public page, anyone can view this page. But if a user has already logon,
   the user infomation will be displayed.</div>
  <div>---------------------------------------------------------</div>
  <div>To <a href="protected">protected</a> page.</div>
  <div>To <a href="protected/admin">admin</a> page.</div>
  <div>To <a href="protected/renew">renew</a> page.</div>
  <div>---------------------------------------------------------</div>
  <div><a href="logout.jsp">logout</a></div>
  
  <div>---------------------------------------------------------</div>
  <div>Wecome ${empty pageContext.request.remoteUser ? "Guest" : pageContext.request.remoteUser}.</div>
  <h3>session</h3>
  <table border="1" style="border-collapse:collapse;border-spacing:0;">
  <tr><th>property/attribute</th><th>value</th></tr>
  <tr><td>session.creationTime</td><td>${pageContext.request.session.creationTime}</td></tr>
  <tr><td>session.id</td><td>${pageContext.request.session.id}</td></tr>
  <tr><td>session.lastAccessedTime</td><td>${pageContext.request.session.lastAccessedTime}</td></tr>
  <tr><td>session.maxInactiveInterval</td><td>${pageContext.request.session.lastAccessedTime}</td></tr>
  <tr><td>session.new</td><td>${pageContext.request.session.new}</td></tr>
  
  <c:forEach items="${pageContext.request.session.attributeNames}" var="attr">
    <tr><td>session["${attr}"]</td><td>${sessionScope[attr] }
    <c:if test="${attr =='_const_cas_assertion_' }">
      <ul>
        <li>validFromDate = ${sessionScope[attr].validFromDate }</li>
        <li>validUntilDate = ${sessionScope[attr]["validUntilDate"] }</li>
        <li>attributes = ${sessionScope[attr]["attributes"] }</li>
      </ul>
    </c:if>
    </td></tr>
  </c:forEach>
  </table>
  
  <h3>request</h3>
  <table border="1" style="border-collapse:collapse;border-spacing:0;">
  <tr><th>property/attribute</th><th>value</th></tr>
  <tr><td>request.authType</td><td>${pageContext.request.authType}</td></tr>
  <tr><td>request.contextPath</td><td>${pageContext.request.contextPath}</td></tr>
  <tr><td>request.cookies</td><td>${pageContext.request.cookies}
    <table border="1" style="border-collapse:collapse;border-spacing:0;">
      <tr><th>name</th><th>value</th><th>comment</th><th>domain</th><th>maxAge</th><th>path</th><th>secure</th><th>version</th><th>httpOnly</th></tr>
      <c:forEach items="${pageContext.request.cookies}" var="c">
        <tr><td>${c.name}</td><td>${c.value}</td><td>${c.comment}</td><td>${c.domain}</td><td>${c.maxAge}</td><td>${c.path}</td><td>${c.secure}</td><td>${c.version}</td><td>${c.httpOnly}</td></tr>
      </c:forEach>
    </table>
  </td></tr>
  <tr><td>request.headerNames</td><td>${pageContext.request.headerNames}</td></tr>
  <tr><td>request.method</td><td>${pageContext.request.method}</td></tr>
  <tr><td>request.pathInfo</td><td>${pageContext.request.pathInfo}</td></tr>
  <tr><td>request.pathTranslated</td><td>${pageContext.request.pathTranslated}</td></tr>
  <tr><td>request.queryString</td><td>${pageContext.request.queryString}</td></tr>
  <tr style="color:red;"><td>request.remoteUser</td><td>${pageContext.request.remoteUser}</td></tr>
  <tr><td>request.requestedSessionId</td><td>${pageContext.request.requestedSessionId}</td></tr>
  <tr><td>request.requestURI</td><td>${pageContext.request.requestURI}</td></tr>
  <tr><td>request.requestURL</td><td>${pageContext.request.requestURL}</td></tr>
  <tr><td>request.servletPath</td><td>${pageContext.request.servletPath}</td></tr>
  <tr style="color:red;"><td>request.userPrincipal</td><td>${pageContext.request.userPrincipal}
    <ul>
      <li>class = ${pageContext.request.userPrincipal.class}</li>
      <li>name = ${pageContext.request.userPrincipal.name}</li>
      <li>attributes = ${pageContext.request.userPrincipal.attributes}</li>
    </ul>
  </td></tr>
  <tr><td>request.requestedSessionIdFromCookie</td><td>${pageContext.request.requestedSessionIdFromCookie}</td></tr>
  <tr><td>request.requestedSessionIdFromURL</td><td>${pageContext.request.requestedSessionIdFromURL}</td></tr>
  <tr><td>request.requestedSessionIdValid</td><td>${pageContext.request.requestedSessionIdValid}</td></tr>
  <tr><td>request.characterEncoding</td><td>${pageContext.request.characterEncoding}</td></tr>
  <tr><td>request.contentLength</td><td>${pageContext.request.contentLength}</td></tr>
  <tr><td>request.contentType</td><td>${pageContext.request.contentType}</td></tr>
  <tr><td>request.dispatcherType</td><td>${pageContext.request.dispatcherType}</td></tr>
  <tr><td>request.localAddr</td><td>${pageContext.request.localAddr}</td></tr>
  <tr><td>request.locale</td><td>${pageContext.request.locale}</td></tr>
  <tr><td>request.locales</td><td>${pageContext.request.locales}</td></tr>
  <tr><td>request.localName</td><td>${pageContext.request.localName}</td></tr>
  <tr><td>request.localPort</td><td>${pageContext.request.localPort}</td></tr>
  <tr><td>request.protocol</td><td>${pageContext.request.protocol}</td></tr>
  <tr><td>request.remoteAddr</td><td>${pageContext.request.remoteAddr}</td></tr>
  <tr><td>request.remoteHost</td><td>${pageContext.request.remoteHost}</td></tr>
  <tr><td>request.remotePort</td><td>${pageContext.request.remotePort}</td></tr>
  <tr><td>request.schema</td><td>${pageContext.request.scheme}</td></tr>
  <tr><td>request.serverName</td><td>${pageContext.request.serverName}</td></tr>
  <tr><td>request.serverPort</td><td>${pageContext.request.serverPort}</td></tr>
  <tr><td>request.asyncStarted</td><td>${pageContext.request.asyncStarted}</td></tr>
  <tr><td>request.asyncSupported</td><td>${pageContext.request.asyncSupported}</td></tr>
  <tr><td>request.secure</td><td>${pageContext.request.secure}</td></tr>

  <c:forEach items="${pageContext.request.parameterNames}" var="p">
    <tr><td>request.parameter["${p}"]</td><td>${param[p]}</td></tr>
  </c:forEach>
  
  <c:forEach items="${pageContext.request.attributeNames}" var="attr">
    <tr><td>request["${attr}"]</td><td>${requestScope[attr] }</td></tr>
  </c:forEach>
  </table>

</body>
</html>
