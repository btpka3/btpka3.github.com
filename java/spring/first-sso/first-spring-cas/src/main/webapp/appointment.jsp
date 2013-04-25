<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="sec" uri="http://www.springframework.org/security/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>预约列表</title>
</head>
<body>
  <div>
    <h6>预约列表</h6>
    <ul>
      <li>10:15 小明</li>
      <li>11:00 小红</li>
      <li>14:30 小小</li>
    </ul>
  </div>
  <div><a href="<c:url value="/"/>">返回</a></div>
</body>
</html>