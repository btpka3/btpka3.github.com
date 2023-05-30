FROM apache/dubbo-admin:0.5.0

# Dockerfile 如果不指定 WORKDIR, 默认是 /
# https://docs.docker.com/engine/reference/builder/#workdir
#
# 而根据spring boot 的文档，会按照以下顺序查找 application.properties.
# 如果同时存在多个，则会逆序用后者覆盖前者（按单个KEY-VALUE方式覆盖）
# 1. ${WORKDIR}/config/application.properties   # 当前Dockerfile 就是使用的这种方式
# 2. ${WORKDIR}/application.properties
# 3. classpath:/config/application.properties
# 3. classpath:/application.properties

# https://docs.spring.io/spring-boot/docs/2.3.12.RELEASE/reference/htmlsingle/#boot-features-external-config-application-property-files
# https://docs.spring.io/spring-boot/docs/2.3.12.RELEASE/reference/htmlsingle/#howto-change-the-location-of-external-properties
COPY application.properties /config/
#ENTRYPOINT tail -f /dev/null
