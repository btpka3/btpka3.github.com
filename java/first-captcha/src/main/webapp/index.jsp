<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" session="false"%>
<%@ taglib prefix="c"     uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"   uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:url var="userUrl" value="/ws/user" />
<html>
<head>
<title>first captcha</title>
<link rel="stylesheet" href="//ajax.googleapis.com/ajax/libs/dojo/1.9.1/dijit/themes/claro/claro.css" />
<script type="text/javascript" src="//ajax.googleapis.com/ajax/libs/dojo/1.9.1/dojo/dojo.js" ></script>
<script type="text/javascript">
require( [
    "dijit/form/Button",
    "dojo/_base/window",
    "dojo/dom-style",
    "dojo/dom-construct",
    "dojo/dom-class",
    "dojo/request",
    "dojo/dom",
    "dojo/on",
    "dijit/Dialog",
    "dojo/domReady!"
], function(
    Button,
    win,
    style,
    ctr,
    domClass,
    request,
    dom,
    on,
    Dialog
) {
    function loadNewCaptcha(){
        var reqPromise = request("/rs/captcha/", {method:"POST"});
        reqPromise.response.then(function(resp){
            console.debug("aaaaaaaaaaaaa");
            dom.byId("captchaImg").src = resp.getHeader("Location");

        }, function(err){
            new Dialog({
                title: "Error",
                content: "Could not load captcha :" + err,
                style: "width: 300px"
            }).show();
        });
    }
    loadNewCaptcha();
    on(captchaImg,"click", loadNewCaptcha);

    new Button({
        label:"Refresh",
        onClick: loadNewCaptcha
    }, "refreshCaptcha");

    new Button({
        label:"Submit",
        onClick: function(){
            var reqPromise = request(dom.byId("captchaImg").src, {
                method:"POST",
                data:{ answer: dom.byId("userInput").value}
            });
            reqPromise.then(function(result){
                new Dialog({
                    title: "Result:"+result,
                    content: result=="true"?"Correct":"Wrong",
                    style: "width: 300px"
                }).show();
            });
        }
    }, "submitAnswer");
});
</script>
</head>
<body class="claro">

<form id="captchaForm">
  <div><img id="captchaImg" style="width:300px; height:60px;"/></div>
  <div><input type="text" id="userInput"/></div>
  <div>
    <button id="refreshCaptcha" >刷新</button>
    <button id="submitAnswer" >提交</button>
    </div>
</form>
</body>
</html>

