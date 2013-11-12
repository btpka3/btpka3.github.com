<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false" isErrorPage="true"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"    uri="http://java.sun.com/jsp/jstl/functions" %>

<c:set var="exception" value="${requestScope['javax.servlet.error.exception']}"/>
<c:set var="errMsg" value="${requestScope['javax.servlet.error.message']}"/>

<html>
<body>
<div style="border:1px solid red;">${fn:length(exception.message)>0 ? fn:escapeXml(exception.message) : 'ERROR' }</div>

<!--
javax.servlet.error.message=${fn:escapeXml(errMsg) }

<c:if test="${exception!=null}">
<spring:eval htmlEscape="true" expression="T(org.apache.commons.lang3.exception.ExceptionUtils).getStackTrace(exception)"/>
</c:if>
-->
</body>
</html>

