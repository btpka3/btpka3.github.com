<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c"   uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"  uri="http://java.sun.com/jsp/jstl/functions" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>testProxy Result</title>
</head>
<body>
  <div> appointmentServiceUrl = 【<c:out value="${fn:escapeXml(appointmentServiceUrl)}"></c:out>】</div>
  <div> appointmentServiceRespCode = 【${appointmentServiceRespCode}】</div>
  <div> appointmentServiceResp = 【<pre><c:out value="${appointmentServiceResp}"></c:out></pre>】</div>
  <hr/>
  <div> staffServiceUrl = 【<c:out value="${fn:escapeXml(staffServiceUrl)}"></c:out>】</div>
  <div> staffServiceRespCode = 【${staffServiceRespCode}】</div>
  <div> staffServiceResp = 【<pre><c:out value="${staffServiceResp}"></c:out></pre>】</div>
</body>
</html>