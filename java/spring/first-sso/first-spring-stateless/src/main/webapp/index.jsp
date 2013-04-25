<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>欢迎</title>
</head>
<body>
<div>该应用不应当让用户直接使用浏览器查看，而是仅仅提供RESTFul服务。</div>
<hr/>
<sec:authorize access="isAnonymous()">
    <div>您尚未登录。</div>
</sec:authorize>
<sec:authorize access="isAuthenticated()">
  <div>
    您已经登录，用户名是
    <sec:authentication property="principal.username" />。
  </div>
</sec:authorize>

<hr/>
<div><a href="<c:url value="/appointment.jsp"/>">预约数据</a></div>
<div><a href="<c:url value="/staff.jsp"/>">员工数据</a></div>

<br/>
<div>
<p>
注意：假如两个应用A、B均是Stateful的应用，且在同一个域下，且均要使用CAS的单点登录服务。用户已经在应用A单点登录，而尚未在应用B登录。
应用A要通过Ajax调用应用B的服务，直接调用是不行的，因为会从HTTP切换到HTTPS，从应用B调转到CAS进行登录验证，这就牵涉到了跨域。
处理方式可以是：应用B对外提供一个 /B/isLogined 的服务，返回是否已经登录，如果没登陆，应用A的JavaScript应当在当前DOM中创建一个隐藏的iframe，
路径是应用B的 /B/mustLogined 路径，让浏览器进行这个跳转操作。最后应用A再检查 /B/isLogined 如果返回true，就可以调用了。
</p>
未曾单点登录时，下面的iframe（应当被隐藏掉）的内容是CAS的登陆画面。如果已经登录，就可以显示业务内容。
</div>

<iframe src="/first-spring-cas/sec.jsp" style="width:600px; height:300px; border:1px solid red; overflow: auto;"></iframe>

</body>
</html>