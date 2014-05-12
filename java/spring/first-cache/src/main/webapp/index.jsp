<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="userUrl" value="/ws/user" />
<html>
<body>
<div><a href='${userUrl}'>user</a></div>
</body>
</html>

