<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>index</title>
</head>

<body>
<ol>
    <li>model 测试:
        <ul><li>a: ${c}</li>
            <li>b: ${b}</li>
            <li>c: ${c}</li></ul>
    </li>
    <li>Service调用测试 <g:link action="add">add</g:link></li>
    <li>mongo 测试:
        <ul>
            <li>插入 <g:link action="insert">insert</g:link></li>
            <li>列表 <g:link action="list">list</g:link></li>
            <li>清空 <g:link action="clear">clear</g:link></li>
        </ul>
    </li>
    <li>Mongo mapWith 测试:
        <ul>
            <li>插入 <g:link action="insertMongo">insertMongo</g:link></li>
            <li>列表 <g:link action="listMongo">listMongo</g:link></li>
            <li>使用底层API <g:link action="testMongo">testMongo</g:link></li>
        </ul>
    </li>
</ol>

</body>
</html>