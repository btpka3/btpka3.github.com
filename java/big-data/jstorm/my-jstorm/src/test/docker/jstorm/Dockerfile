FROM openjdk:8u131-jdk-alpine

RUN \
    apk add --no-cache openssl bash shadow tzdata \
    mkdir /tmp/jstorm \
    && wget https://github.com/alibaba/jstorm/releases/download/2.2.1/jstorm-2.2.1.zip \
    && unzip jstorm-2.2.1.zip \
    && mv jstorm-2.2.1 /jstorm \
    && ln
    && usermod -s /bin/bash root \
    && cp /usr/share/zoneinfo/Asia/Shanghai /etc/localtime \
    && echo "Asia/Shanghai" > /etc/timezone \
    && echo ". /etc/profile" > /root/.bashrc \

ENV JSTORM_HOME=/jstorm
java " + jvmtype + " -Djstorm.home=" + JSTORM_DIR + " " + get_config_opts() + " -Djava.library.path=" + nativepath + " " + childopts + " -cp " + get_classpa
th(extrajars) + " " + klass + " " + args_str

CMD \
    java \
        -Djstorm.home=$JSTORM_HOME \
        dd



