<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>


<html>
<head>
<title>login </title>
</head>
<body>
<!--
<div>${flowExecutionUrl}</div>
<div>${flowExecutionKey}</div>
<div>${flowRequestContext}</div>
<div>============${flowRequestContext.flowScope }</div>
 -->

<div> Login:
<form action="${flowExecutionUrl}" method="post">
  <div>user name : <input type="text" name="userName" value="zhang3" /></div>
  <div>password : <input type="password" name="password" value="" /></div>
  <div><input type="submit" name="_eventId_submit" value="Submit" /></div>
</form>
</div>
<br/>
<div> your have try ${errorCount } times. (Tips: userName and password should be same)</div>
</body>
</html>
