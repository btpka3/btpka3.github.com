<%@ page import="grails.plugin.springsecurity.SpringSecurityUtils" contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>index</title>
</head>

<body>
<ol>
    <li>model 测试:
        <ul><li>a: ${a}</li>
            <li>b: ${b}</li>
            <li>c: ${c}</li></ul>
    </li>
    <li>Service调用测试 <g:link action="add">add</g:link></li>
    <li>conf 测试 <g:link action="conf">conf</g:link></li>
    <li>mongo 测试:
        <ul>
            <li>插入 <g:link action="insert">insert</g:link></li>
            <li>列表 <g:link action="list">list</g:link></li>
            <li>清空 <g:link action="clear">clear</g:link></li>
            <li>使用底层API <g:link action="testMongo">testMongo</g:link></li>
        </ul>
    </li>
    <li>Spring Security 测试:
        <ul>
            <li>初始化用户 <g:link action="setupUsers">setupUsers</g:link></li>
            <li>sec <g:link action="sec">sec</g:link></li>
            <li>admin <g:link action="admin">admin</g:link></li>
            <li>logout <g:link uri="${grails.plugin.springsecurity.SpringSecurityUtils.securityConfig.logout.filterProcessesUrl}" >logout</g:link></li>
        </ul>
    </li>

    <li>ElasticSearch 测试:
        <ul>
            <li>es <g:link action="es">es</g:link></li>
        </ul>
    </li>
</ol>

</body>
</html>