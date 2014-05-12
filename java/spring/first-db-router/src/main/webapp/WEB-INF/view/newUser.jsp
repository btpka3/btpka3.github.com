<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>new user</title>
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

<h1>new user</h1>
<form action="list.do" method="post">
<div>

<table class="mystyle">
  <tr>
    <th>ID</th><td>&nbsp;</td>
  </tr>
  <tr>
    <th>HOSPITAL_ID *</th>
    <td>
      <select name="hospitalId">
        <option value="1" >1</option>
        <option value="2" >2</option>
        <option value="3" >3</option>
      </select>
    </td>
  </tr>
  <tr>
    <th>NAME *</th><td><input type="text" name="name" size="10" value="${user.remark}"/></td>
  </tr>
  <tr>
    <th>REMARK</th><td> <input type="text" name="remark" size="10" value="${user.remark}"/></td>
  </tr>
</table>
</div>
<div><input type="submit" value="new"/> <a href="list.do"><button>back to list</button></a> </div>
</form>

</body>
</html>