<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>user detail</title>
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

<h1>user detail</h1>
<form action="detail.do" method="post">
<div>
<input type="hidden" name="_method" value="PUT" />
<input type="hidden" name="hospitalId" value="${user.hospitalId}" />
<input type="hidden" name="userId" value="${user.id}" />

<table class="mystyle">
  <tr>
    <th>ID</th><td>${user.id}</td>
  </tr>
  <tr>
    <th>HOSPITAL_ID</th><td>${user.hospitalId}</td>
  </tr>
  <tr>
    <th>NAME</th><td>${user.name}</td>
  </tr>
  <tr>
    <th>REMARK</th><td> <input type="text" name="remark" size="10" value="${user.remark}"/></td>
  </tr>
</table>
</div>
<div><input type="submit" value="update"/> <a href="list.do"><button>back to list</button></a> </div>
</form>

</body>
</html>