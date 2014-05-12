<%@ tag language="java" pageEncoding="UTF-8" body-content="empty" trimDirectiveWhitespaces="true" description="从一组bean的列表中，按给定的单个值找到bean后，输入指定的属性" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="spring"  uri="http://www.springframework.org/tags"%>
<%@ attribute name="items" required="true" rtexprvalue="true" description="bean的列表" type="java.lang.Iterable" %>
<%@ attribute name="itemValue" required="true" rtexprvalue="true" description="用于匹配指定值的bean的属性名" %>
<%@ attribute name="value" required="true" rtexprvalue="true" description="要匹配的值的" %>
<%@ attribute name="itemLabel" required="true" rtexprvalue="true" description="要输出的bean的属性名" %>
<c:forEach items="${items}" var="item">
  <c:if test="${item[itemValue]==value}">${fn:escapeXml(item[itemLabel])}</c:if>
</c:forEach>