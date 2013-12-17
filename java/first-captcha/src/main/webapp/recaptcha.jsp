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
    "dijit/form/Form",
    "dojo/dom-form",
    "dijit/registry",
    "http://www.google.com/recaptcha/api/js/recaptcha_ajax.js",
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
    Dialog,
    Form,
    domForm,
    registry,
    recaptchaJs
) {
    //new Form(null, "captchaForm");
    Recaptcha.create("6Le098kSAAAAAC9wOn_gKxadNxcy_caLokseFNVY", "recaptcha_div", {
        theme: "red",
        callback: Recaptcha.focus_response_field
    });

    new Button({
        label:"Submit",
        onClick: function(){
            console.info(domForm.toObject("captchaForm"));
            var reqPromise = request("/rs/recaptcha/", {
                method:"POST",
                data:domForm.toObject("captchaForm")
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
  <div id="recaptcha_div"></div>
  <div>
    <button id="submitAnswer" >提交</button>
  </div>
</form>
</body>
</html>

