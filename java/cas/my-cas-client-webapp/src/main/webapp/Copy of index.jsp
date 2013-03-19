<%@page contentType="text/html; charset=UTF-8"%>
<%@page pageEncoding="UTF-8"%>
<%@ page import="java.util.Map"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="org.jasig.cas.client.authentication.AttributePrincipal"%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
   "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>CAS Test</title>
</head>
<body>

	<h1>CAS Test</h1>

	<p><%=request.getRemoteUser()%></p>
	<p><%=session.getAttribute("_const_cas_assertion_")%></p>
	<p>===<%=request.getUserPrincipal()%></p>
	<%
		AttributePrincipal principal = (AttributePrincipal) request
				.getUserPrincipal();
	  if(principal!=null){
			Map attributes = principal.getAttributes();
	
			Iterator attributeNames = attributes.keySet().iterator();
	
			out.println("<table>");
	
			for (; attributeNames.hasNext();) {
				out.println("<tr><th>");
				String attributeName = (String) attributeNames.next();
				out.println(attributeName);
				out.println("</th><td>");
				Object attributeValue = attributes.get(attributeName);
				out.println(attributeValue);
				out.println("</td></tr>");
			}
	
			out.println("</table>");
	  }
	%>
</body>
</html>
