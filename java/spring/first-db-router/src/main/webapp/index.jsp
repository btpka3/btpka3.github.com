<%@ page language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt"     uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn"      uri="http://java.sun.com/jsp/jstl/functions" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<style type="text/css">
span.a{
  display: inline-block;
  width: 120px;
}
</style>
<title>welcome</title>
</head>
<body>


<div><a href='user/list.do' target="list">DB路由测试</a>
<ul>测试单数据库的：
  <li>增</li>
  <li>删</li>
  <li>改</li>
  <li>查</li>
</ul>
</div>

<div>事务控制测试
<ul>确认多数据库操作时结果符合预期（实际是required事务不符合预期），开发中应当避免跨数据库：
  <li><a href="/test/reset.do" target="test"><button>Reset DB</button></a></li>
  <li><a href="test/requiredTransSucceed.do" target="test"><button>requiredTransSucceed</button></a></li>
  <li><a href="test/requiredTransFailedAt0.do" target="test"><button>requiredTransFailedAt0</button></a></li>
  <li><a href="test/requiredTransFailedAt1.do" target="test"><button>requiredTransFailedAt1</button></a></li>
  <li><a href="test/requiresNewTransSucceed.do" target="test"><button>requiresNewTransSucceed</button></a></li>
  <li><a href="test/requiresNewTransFailedAt0.do" target="test"><button>requiresNewTransFailedAt0</button></a></li>
  <li><a href="test/requiresNewTransFailedAt1.do" target="test"><button>requiresNewTransFailedAt1</button></a></li>
</ul>

</div>

<div>数据库列表
  <ol>可通过<a href="http://localhost:9090/" target="db">这里</a>登录查看（注意：同时只能登录一个）。
    <li>first-db-router1
        <ul>
          <li><span class="a">JDBC Driver </span>: org.h2.Driver </li>
          <li><span class="a">JDBC URL    </span>: jdbc:h2:tcp://localhost:9001/~/.h2/first-db-router1;MVCC=TRUE </li>
          <li><span class="a">JDBC 用户名 </span>: db1</li>
          <li><span class="a">JDBC 密码   </span>: 123456</li>
          <li><span class="a">数据库存放路径   </span>: ~/.h2/first-db-router1</li>
        </ul>
    </li>
    <li>first-db-router2
        <ul>
          <li><span class="a">JDBC Driver </span>: org.h2.Driver </li>
          <li><span class="a">JDBC URL    </span>: jdbc:h2:tcp://localhost:9002/~/.h2/first-db-router2;MVCC=TRUE </li>
          <li><span class="a">JDBC 用户名 </span>: db2</li>
          <li><span class="a">JDBC 密码   </span>: 123456</li>
          <li><span class="a">数据库存放路径   </span>: ~/.h2/first-db-router2</li>
        </ul>
    </li>
  </ol>
</div>
</body>
</html>