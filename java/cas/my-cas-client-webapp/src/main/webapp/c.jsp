<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=GBK">
<title>test tomcat cluster : Page C</title>
</head>
<body>
<%
    session.invalidate();
%>
<div>cur path = [<%=request.getRealPath(".")%>]</div>
<div>cur session = [<%=session%>], session id = [<%=session.getId() %>]</div>
<div>cur session has bean invalidated。</div>
</body>
</html>