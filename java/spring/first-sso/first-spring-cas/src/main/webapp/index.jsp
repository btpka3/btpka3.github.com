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
    <script src="//ajax.googleapis.com/ajax/libs/jquery/1.10.1/jquery.min.js"></script>
    <script src="<c:url value="/login.js"/>"></script>

    <div>您尚未登录，请先<a href="<c:url value="/login.jsp"/>">单点登录</a> :</div>
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
          <td>123456</td>
          <td>可以查看预约记录</td>
        </tr>
        <tr>
          <td>li4</td>
          <td>123456</td>
          <td>可以查看员工信息</td>
        </tr>
        <tr>
          <td>wang5</td>
          <td>123456</td>
          <td>全部</td>
        </tr>
      </table>
    </div>
    <div><button id="ajaxLogin">Ajax登录</button></div>
    <div id="ajaxLoginDiv" style="display:none; background-color: silver;" >
      <div>异步登录</div>
      <form id="ajaxLoginForm">
      <div><label for="name">用户名</label>:<input type="text" id="ajaxLoginName" name="name" value="zhang3" /></div>
      <div><label for="ajaxLoginPassword" >密码</label>:<input type="text" id="ajaxLoginPassword" name="password" value="123456" /></div>
      <div><button id="ajaxLoginSubmit">登录</button></div>
      </form>
    </div>
</sec:authorize>

<sec:authorize access="isAuthenticated()">
  <div>
    您已经登录，用户名是
    <sec:authentication property="principal.username" />。
  </div>
  <div>【<a href="<c:url value="/j_spring_security_logout"/>">本地退出</a>】- 直到碰到需要验证权限的时候，才会重新变为已登录状态（中间经过2次重定向）。</div>
  <div>【<a href="<c:url value="/j_spring_cas_security_logout"/>">单点退出</a>】- 全部站点均退出，再碰到需要权限的时候，需要重新输入用户名、密码进行登录。</div>
</sec:authorize>

<div>
  <a href="<c:url value="/sec.jsp"/>">查看受保护内容</a>
</div>
<div>
  <a href="<c:url value="/testProxy.do"/>">查看调用RESTful服务</a> - 注意：如果是有状态的服务，则这边只能已经登录后才能使用该服务，因为JavaScript程序、Java程序无法、较难实现跨域，跨HTTP/HTTPS的跳转。
</div>
<div>
  <a href="<c:url value="/testCasRESTfulApi.html"/>">查看测试CAS RESTful API</a> - 注意：如果是有状态的服务，则这边只能已经登录后才能使用该服务，因为JavaScript程序、Java程序无法、较难实现跨域，跨HTTP/HTTPS的跳转。
</div>

</body>
</html>