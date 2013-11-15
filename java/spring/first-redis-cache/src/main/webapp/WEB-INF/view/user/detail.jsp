<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>

<html>
<head>
<title>user detail</title>
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
<h1>user detail</h1>
<form:form method="post" commandName="user">
  <table class="mystyle">
    <tr><th>ID</th><td>${user.id} <form:hidden path="id"/></td></tr>
    <tr><th>NAME</th><td><form:input path="name"/></td></tr>
    <tr><th>GENDER</th><td>
        <form:radiobutton path="gender" value="true" id="gender_man"/><label for="gender_man">man</label>
        <form:radiobutton path="gender" value="false" id="gender_woman"/><label for="gender_woman">woman</label></td></tr>
    <tr><th>SIGNATURE</th><td><form:textarea path="signature"/></td></tr>
    <tr><td colspan="2"><input type="submit" value="update"/></td></tr>
  </table>
</form:form>
</body>
</html>
