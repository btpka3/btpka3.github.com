<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
</head>
<body>


  <sec:authorize access="isAnonymous()">
    <div>您尚未登录，请先登录:</div>

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

    <c:if test="${not empty param.login_error}">
      <font color="red"> Your login attempt was not successful, try again.<br /> <br /> Reason: <c:out
          value="${SPRING_SECURITY_LAST_EXCEPTION.message}" />.
      </font>
    </c:if>

    <form name="f" action="<c:url value='j_spring_security_check'/>" method="POST">
      <table>
        <tr>
          <td>User:</td>
          <td><input type='text' name='j_username'
            value='<c:if test="${not empty param.login_error}"><c:out value="${SPRING_SECURITY_LAST_USERNAME}"/></c:if>' /></td>
        </tr>
        <tr>
          <td>Password:</td>
          <td><input type='password' name='j_password'></td>
        </tr>
        <tr>
          <td colspan='2'><input name="submit" type="submit"></td>
        </tr>
      </table>
    </form>
  </sec:authorize>

  <sec:authorize access="isAuthenticated()">
    <div>
      您已经登录，用户名是
      <sec:authentication property="principal.username" />。
      【<a href="<c:url value="/j_spring_security_logout"/>">退出</a>】
    </div>

    <div style="border: 1px solid red;">
      以下是功能区，只有相应权限的人可以看到相应的内容：

      <sec:authorize url="/appointment.jsp">
        <div>
          <a href="<c:url value="/appointment.jsp"/>">预约列表</a>
        </div>
      </sec:authorize>

      <sec:authorize url="/staff.jsp">
        <div>
          <a href="<c:url value="/staff.jsp"/>">员工列表</a>
        </div>
      </sec:authorize>
    </div>
    <br/>

    <div style="border: 1px solid blue;">
      以下是测试区域，测试URL拦截功能：
      <div>
        <a href="<c:url value="/appointment.jsp"/>">预约列表</a>
      </div>
      <div>
        <a href="<c:url value="/staff.jsp"/>">员工列表</a>
      </div>
    </div>
  </sec:authorize>

</body>
</html>