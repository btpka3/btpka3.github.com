<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<title>user list</title>
<style type="text/css">
table.mystyle {
    border-width: 0 0 1px 1px;
    border-spacing: 0;
    border-collapse: collapse;
    border-style: solid;
}

.mystyle td,.mystyle th {
    margin: 0;
    padding: 4px;
    border-width: 1px 1px 0 0;
    border-style: solid;
}
</style>
</head>
<body>
  <table class="mystyle">
    <tr><th>ID</th><th>NAME</th><th>GENDER</th><th>SIGNATURE</th></tr>
<c:forEach items="${userList}" var="user">
    <tr>
      <td><a href="${pageContext.request.contextPath}/ws/user/${user.id}"><c:out value="${user.id}"/></a></td>
      <td><c:out value="${user.name}"/></td>
      <td><c:out value="${user.gender}"/></td>
      <td><c:out value="${user.signature}"/></td>
    </tr>
</c:forEach>
  </table>
</body>
</html>
