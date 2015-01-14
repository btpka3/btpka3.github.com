<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>退出成功</title>
</head>
<body>
  <div style="text-align: center;">您已经成功退出登录！（仅仅退出当前应用，单点登录仍然有效）</div>
  <div style="text-align: center;"><a href="<c:url value="/"/>">返回主页</a></div>
</body>
</html>