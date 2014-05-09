# 目标
基于Servlet API，不依赖于容器，由应用自行管理session，以通过将session集中存储达到集群的功能。

# 约束
* 不能使用HttpSessionListener。
* 不能使用长连接（以提高性能？）
* 必须将me.test.SessionFilter作为第一个filter使用

