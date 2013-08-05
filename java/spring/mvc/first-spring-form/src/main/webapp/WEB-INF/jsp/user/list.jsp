<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="me"      tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>list</title>
<style type="text/css">
table.mystyle {
    border-width: 0 0 1px 1px;
    border-spacing: 0;
    border-collapse: collapse;
    border-style: solid;
}

.mystyle td, .mystyle th {
    margin: 0;
    padding: 4px;
    border-width: 1px 1px 0 0;
    border-style: solid;
}

</style>
</head>
<body>

<h1>用户一览</h1>
<table class="mystyle">
  <tr>
    <th>ID</th>
    <th>Name</th>
    <th>age</th>
    <th>recMail</th>
    <th>hobbys</th>
    <th>gender</th>
    <th>country</th>
    <th>&nbsp;</th>
  </tr>
<c:forEach items="${userList}" var="user">
  <tr>
    <td>${fn:escapeXml(user.id)}</td>
    <td>${fn:escapeXml(user.name)}</td>
    <td>${fn:escapeXml(user.age)}</td>
    <td>${user.recMail?'是':'否'}</td>
    <td><c:forEach items="${user.hobbies}" var="hobby" varStatus="status"><spring:message code="hobbies.${hobby}" text=""/><c:if test="${status.index+1 lt fn:length(user.hobbies)}">,</c:if></c:forEach></td>
    <td><spring:message code="gender.${user.gender}" text="" /></td>
    <td><me:codeLabel items="${countryCodeList}" itemValue="value" itemLabel="label" value="${user.country}" />  </td>
    <td>
      <spring:url var="url" value="/user/edit.do">
        <spring:param name="id" value="${user.id}" />
      </spring:url>
      <a href="${url}">修改</a>
    </td>
  </tr>
</c:forEach>
<c:forEach var="i" begin="${fn:length(userList)}" end="5"  >
  <tr>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
    <td>&nbsp;</td>
  </tr>
</c:forEach>
</table>

</body>
</html>