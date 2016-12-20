<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>静态资源测试</title>

    <!--
    http://www.asset-pipeline.com/manual/#directives
    http://bertramdev.github.io/grails-asset-pipeline/guide/releases.html
    ControllersGrailsPlugin.groovy
    -->

    %{--<asset:stylesheet src="bt.css"/>--}%
    %{--<asset:stylesheet src="application.css"/>--}%

    <asset:stylesheet src="aaa.css"/>
    <asset:stylesheet src="font-awesome/css/font-awesome.css"/>

    <link rel="stylesheet" href="${createLink(uri:'/static/bootstrap/css/bootstrap.css')}" />
</head>

<body>

<ul> asset-pipeline test : font-awesome.css
    <li><i class="fa fa-address-book" aria-hidden="true"></i></li>
    <li><i class="fa fa-bluetooth" aria-hidden="true"></i></li>
</ul>

<ul> bootstrap test : src/main/resources/public
    <li><button type="button" class="btn btn-primary">Primary</button></li>
    <li><p class="text-danger">this is text-danger</p></li>
    <li><span class="glyphicon glyphicon-search"></span></li>
</ul>
</body>
</html>