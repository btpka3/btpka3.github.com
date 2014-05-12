<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
<head>
<title>login </title>
</head>
<body>
<div>welecome ${fn:escapeXml(userName)} !</div>
<c:url var="homeUrl" value="/"/>
<div>Go <a href="${homeUrl}">Home</a></div>
</body>
</html>
