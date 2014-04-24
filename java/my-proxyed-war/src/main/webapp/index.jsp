<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>
<head>
<title>http request info</title>
<style type="text/css">
table.mystyle{
    border-width: 0 0 1px 1px;
    border-spacing: 0;
    border-collapse: collapse;
    border-style: solid;
}

.mystyle td, .mystyle th{
    margin: 0;
    padding: 4px;
    border-width: 1px 1px 0 0;
    border-style: solid;
}
</style>
</head>
<body>

<h2>request info</h2>
<table class="mystyle">
<tr><td>scheme</td><td>${pageContext.request.scheme}</td></tr>

<tr><td>serverName</td><td>${pageContext.request.serverName}</td></tr>
<tr><td>serverPort</td><td>${pageContext.request.serverPort}</td></tr>

<tr><td>localAddr</td><td>${pageContext.request.localAddr}</td></tr>
<tr><td>localName</td><td>${pageContext.request.localName}</td></tr>
<tr><td>localPort</td><td>${pageContext.request.localPort}</td></tr>

<tr><td>remoteAddr</td><td>${pageContext.request.remoteAddr}</td></tr>
<tr><td>remoteHost</td><td>${pageContext.request.remoteHost}</td></tr>
<tr><td>remotePort</td><td>${pageContext.request.remotePort}</td></tr>
<tr><td>remoteUser</td><td>${pageContext.request.remoteUser}</td></tr>

<tr><td>servletPath</td><td>${pageContext.request.servletPath}</td></tr>
<tr><td>pathInfo</td><td>${pageContext.request.pathInfo}</td></tr>
<tr><td>pathTranslated</td><td>${pageContext.request.pathTranslated}</td></tr>
<tr><td>requestURI</td><td>${pageContext.request.requestURI}</td></tr>
<tr><td>requestURL</td><td>${pageContext.request.requestURL}</td></tr>
<tr><td>protocol</td><td>${pageContext.request.protocol}</td></tr>
<tr><td>queryString</td><td>${pageContext.request.queryString}</td></tr>
<tr><td>authType</td><td>${pageContext.request.authType}</td></tr>
<tr><td>secure</td><td>${pageContext.request.secure}</td></tr>
</table>

<h2>http heades</h2>
<table class="mystyle">
<c:forEach var="headerName" items="${pageContext.request.headerNames }" >
<tr><td>${headerName}</td><td>${header[headerName]}</td></tr>
</c:forEach>

<c:url  value="/aaa/bbb" />
</body>
</html>

