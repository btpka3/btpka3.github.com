<%@ page contentType="text/html; charset=UTF-8"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>test tomcat cluster : Page B</title>
</head>
<body>
<%
    String userId = (String) request.getParameter("userId");
    if (session.getAttribute("userId") == null && userId != null) {
        session.setAttribute("userId", userId);
    }
%>
<div>this is a test page</div>
<div>cur path = [<%=request.getRealPath(".") %>]</div>
<div>cur session = [<%=session%>], session id = [<%=session.getId() %>]</div>
<div>cur user = [<%=session.getAttribute("userId")%>]</div>
<div><a href="b.jsp">refresh</a></div>
<form action="c.jsp" method="POST" >
<div><input type="submit" value="invalidate cur session"/></div>
</form>
</body>
</html>