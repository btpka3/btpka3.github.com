FROM eclipse-temurin:8-jre-alpine



# https://github.com/apache/rocketmq-dashboard/blob/rocketmq-dashboard-1.0.0/src/main/docker/Dockerfile
VOLUME /tmp

# https://repo1.maven.org/maven2/org/apache/rocketmq/rocketmq-dashboard/1.0.0/rocketmq-dashboard-1.0.0.jar
# COPY https://maven.aliyun.com/repository/public/org/apache/rocketmq/rocketmq-dashboard/1.0.0/rocketmq-dashboard-1.0.0.jar rocketmq-dashboard.jar
# COPY ~/.m2/repository/org/apache/rocketmq/rocketmq-dashboard/1.0.0/rocketmq-dashboard-1.0.0.jar /rocketmq-dashboard.jar
# 请先下载 rocketmq-dashboard-1.0.0.jar 到本地目录
COPY rocketmq-dashboard-1.0.0.jar /rocketmq-dashboard.jar
RUN sh -c 'touch /rocketmq-dashboard.jar'
ENV JAVA_OPTS=""
ENTRYPOINT [ "sh", "-c", "java $JAVA_OPTS -jar /rocketmq-dashboard.jar" ]

