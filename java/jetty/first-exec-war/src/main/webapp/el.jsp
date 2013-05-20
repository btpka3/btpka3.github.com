<?xml version="1.0" encoding="UTF-8" ?>
<%@page import="java.util.Calendar"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Expression Language Test</title>
</head>
<body>
<%--
<div>session.id = ${session}</div>
<div>session.id = ${session.id}</div>
<div>session.id = ${request}</div>
<div>request.requestURI = ${request.requestURI}</div>
 --%>
<div>param.user = ${param.user}</div>
</body>
</html>