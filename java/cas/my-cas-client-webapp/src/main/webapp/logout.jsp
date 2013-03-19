<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Logout Page</title>
</head>
<body>
  <h1>Logout Page</h1>
  <div>This is a logout page, and it will invalidate current session.</div>
  <% 
     request.getSession().invalidate();
  %>
  <div>---------------------------------------------------------</div>
  <div>To <a href="./">public</a> page.</div>
  <div>To <a href="protected">protected</a> page.</div>
  <div>To <a href="protected/admin">admin</a> page.</div>
  <div>To <a href="protected/renew">renew</a> page.</div>
  <div>---------------------------------------------------------</div>
  <h3>NOTICE</h3>
  <p>You have been logged out of "my-cas-client-webapp".
     To log out of all applications, click <a target="_blank" href="https://localhost:10001/logout">here</a>. </p>
  
  
</body>
</html>
