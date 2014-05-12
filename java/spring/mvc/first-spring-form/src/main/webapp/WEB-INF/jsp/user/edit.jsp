<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form"    uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="me"      tagdir="/WEB-INF/tags"%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>user</title>
<style type="text/css">
.error{
  color: red;
}
</style>
</head>
<body>

<h1>更新用户</h1>
<form:form commandName="u" action="edit.do">
<spring:hasBindErrors name="u" >
  <div class="error">
    <ul>
      <c:forEach var="error" items="${errors.allErrors}">
        <li><spring:message code="${error.codes[0]}"  arguments="${error.arguments}" text=""/><%-- ${error.defaultMessage} --%></li>
      </c:forEach>
    </ul>
  </div>
</spring:hasBindErrors>

  <div>
    <label>id</label>:
    ${fn:escapeXml(u.id)}
    <form:hidden path="id"/>
  </div>

  <div>
    <label>name</label>:
    <form:input path="name" />
    <form:errors path="name" cssClass="error" />
  </div>

  <div>
    <label>age</label>:
    <form:input path="age" />
    <form:errors path="age" cssClass="error" />
  </div>

  <div>
    <label>recMail</label>:
    <form:radiobutton path="recMail" value="true" /> 接收
    <form:radiobutton path="recMail" value="false"/> 不接收
  </div>

  <div>
    <label>hobbies</label>:
    <form:checkbox path="hobbies" value="1"/> <spring:message code="hobbies.1"  text="" />
    <form:checkbox path="hobbies" value="2"/> <spring:message code="hobbies.2"  text="" />
    <form:checkbox path="hobbies" value="3"/> <spring:message code="hobbies.3"  text="" />
  </div>

  <div>
    <label>gender</label>:
    <form:select path="gender">
      <form:option value="0"><spring:message code="gender.0"  text="" /></form:option>
      <form:option value="1"><spring:message code="gender.1"  text="" /></form:option>
      <form:option value="2"><spring:message code="gender.2"  text="" /></form:option>
    </form:select>
  </div>

  <div>
    <label>country</label>:
    <form:select path="country">
      <form:option value="">--请选择--</form:option>
      <form:options items="${countryCodeList}" itemValue="value" itemLabel="label"/>
    </form:select>
  </div>

  <div>
    <input type="submit" value="submit" />
  </div>

</form:form>

</body>
</html>