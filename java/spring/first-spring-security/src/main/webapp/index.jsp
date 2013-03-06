<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="sec"
  uri="http://www.springframework.org/security/tags"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title></title>
</head>
<body>

  <div>
    用户列表
    <table>
      <tr>
        <th>用户名</th>
        <th>密码</th>
        <th>权限</th>
      </tr>
      <tr>
        <td>zhang3</td>
        <td>123</td>
        <td>可以查看预约记录</td>
      </tr>
      <tr>
        <td>li4</td>
        <td>123</td>
        <td>可以查看员工信息</td>
      </tr>
      <tr>
        <td>wang5</td>
        <td>123</td>
        <td>全部</td>
      </tr>
    </table>
  </div>

  <sec:authentication property="" htmlEscape="" scope="" var="" />

  <sec:accesscontrollist hasPermission="" domainObject="" var="">
  </sec:accesscontrollist>

  <sec:authorize access="" ifAllGranted="" ifAnyGranted=""
    ifNotGranted="" method="" url="" var=""></sec:authorize>




</body>
</html>