<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user list</title>
<style type="text/css">
 table.mystyle{
    border-width: 0 0 1px 1px;
    border-spacing: 0;
    border-collapse: collapse;
    border-style: solid;
}

.mystyle td, .mystyle th{
    margin: 0;
    padding: 4px;
    border-width: 1px 1px 0 0;
    border-style: solid;
}
</style>
</head>
<body>
<h1>user list</h1>
<div>
<form style="display: inline;">
hospital :
  <select name="hospitalId">
    <option value="">All</option>
    <option value="1" <c:if test="${param['hospitalId'] == '1' }">selected="selected"</c:if> >1</option>
    <option value="2" <c:if test="${param['hospitalId'] == '2' }">selected="selected"</c:if>>2</option>
    <option value="3" <c:if test="${param['hospitalId'] == '3' }">selected="selected"</c:if>>3</option>
  </select>
  <input type="submit" value="search"/>
</form>
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<a href="newUser.do"><button>new user</button></a>
</div>
<div>
<table class="mystyle">
  <tr>
    <th>ID</th>
    <th>HOSPITAL_ID</th>
    <th>NAME</th>
    <th>REMARK</th>
    <th >&nbsp;</th>
  </tr>
<c:forEach var="user" items="${userList}">
  <tr>
    <td>${user.id}</td>
    <td>${user.hospitalId}</td>
    <td>${user.name}</td>
    <td>${user.remark}</td>
    <td>
      <a href="detail.do?hospitalId=${user.hospitalId}&userId=${user.id}"><button>detail</button></a>
      <form action="detail.do?hospitalId=${user.hospitalId}&userId=${user.id}" method="post" style="display: inline;"><input type="hidden" name="_method" value="DELETE"/><input type="submit" value="delete" /></form>
    </td>
  </tr>
</c:forEach>
</table>
</div>

</body>
</html>