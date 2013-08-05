故障：Tomcat 6 中：
java.lang.NoSuchMethodError: javax.el.ExpressionFactory.newInstance()Ljavax/el/ExpressionFactory;
  org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm.<clinit>(InterpolationTerm.java:60)
  org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator.interpolateExpression(ResourceBundleMessageInterpolator.java:227)
  org.hibernate.validator.messageinterpolation.ResourceBundleMessageInterpolator.interpolateMessage(ResourceBundleMessageInterpolator.java:187)

参考：
  https://hibernate.atlassian.net/browse/HV-792

<dependency>
    <groupId>javax.el</groupId>
    <artifactId>javax.el-api</artifactId>
    <version>2.2.4</version>
</dependency>
<dependency>
    <groupId>org.glassfish.web</groupId>
    <artifactId>javax.el</artifactId>
    <version>2.2.4</version>
</dependency>


启动
mvn jetty:run
mvn jetty:start

停止
mvn jetty:stop


hibernate validator 错误消息查找顺序
1. classPath 下查找 bundleName = ValidationMessages 的 properties 文件
2. 使用默认值 bundleName = org.hibernate.validator.ValidationMessages 的 properties 文件


校验流程：
1. 类型转换
   错误代码的查找请参考 org.springframework.validation.DefaultMessageCodesResolver 的 javadoc
   如果 objectName = "user", field = "age"，则message code按以下方式查找
   (objectName 可以通过对参数使用 @ModelAttribute("user") 设定，或者model.addAttribute("user", xxx)  进行设定)
    1. try "typeMismatch.user.age"
    2. try "typeMismatch.age"
    3. try "typeMismatch.int"
    4. try "typeMismatch"
  因此，如果想消息能唯一，则只能把 objectName 设置的唯一了（较大的Web工程中很男做到，但是如果把所有model都放到同一个package下，且均把类名首字母小写后作为 objectName应该就可以了），
  或者使用 "typeMismatch.int" 这种方式泛泛的提供错误消息了 。


2. BeanValidator
   * 可以通过  @Size(max = 10, message = "{me.test.User.name.Size.message}") 方式提供唯一的【默认】错误消息。
   * 也同时可以使用以下错误消息
    1. try "Size.user.name"
    2. try "Size.name"
    3. try "Size.java.lang.String"
    4. try "Size"
   <spring:message code="${error.code}" arguments="${error.arguments}" text=""/>
   但是建议使用第一种，因为第一种消息能唯一，而第二种还存在 无法使用{max}，{min} 对message替换、只能通过 {0},{2}进行替换，且顺序不明确的问题。


3. 业务逻辑验证（比如：记录存在性，多属性的关联验证——起始日期必须早于结束日期等）
   使用 errors.rejectValue("fieldName", "errorCode");


JSP 页面显示错误消息
1.  应推荐使用此方法：
    <form:errors path="age" cssClass="error" />
2.  应避免使用这种方式。
  如果使用，
    则比如对于 BeanValidator 最方便的是使用 ${error.defaultMessage} 方法，
    而对于 类型转换和业务逻辑验证最好使用 <spring:message code="${error.codes[0]}"  arguments="${error.arguments}" text=""/> 方法
    而造成不一致。

<spring:hasBindErrors name="u" >
  <div class="error">
    <ul>
      <c:forEach var="error" items="${errors.allErrors}">
        <li><spring:message code="${error.codes[0]}"  arguments="${error.arguments}" text=""/><%-- ${error.defaultMessage} --%></li>
      </c:forEach>
    </ul>
  </div>
</spring:hasBindErrors>

