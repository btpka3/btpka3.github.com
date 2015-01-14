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

  <div style="border: 1px solid red;">
    以下是功能区，通过 JSP 标签控制板块的显示与否。只有相应权限的人可以看到相应的内容：

    <sec:authorize access="hasAnyRole('XROLE_READ_APPOINTMENT')">
      <div>
        <a href="<c:url value="appointment.jsp"/>">预约列表</a>
      </div>
    </sec:authorize>

    <sec:authorize access="hasAnyRole('XROLE_READ_STAFF')">
      <div>
        <a href="<c:url value="staff.jsp"/>">员工列表</a>
      </div>
    </sec:authorize>

  </div>

  <div>&nbsp;</div>
  
  <div style="border: 1px solid blue;">
    以下是测试区，可以尝试直接访问URL(即使没有相应的权限，但访问会出错 401 )：
      <div>
        <a href="<c:url value="appointment.jsp"/>">预约列表 (直接检测权限)</a>
      </div>
      <div>
        <a href="<c:url value="staff.jsp"/>">员工列表 (直接检测权限)</a>
      </div>
  </div>

  <div><a href="<c:url value="/"/>">返回</a></div>
</body>
</html>