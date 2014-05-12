
1. download simplecaptcha-1.2.1.jar and maven install
    mvn install:install-file -Dfile=simplecaptcha-1.2.1.jar -DgroupId=nl.captcha -DartifactId=simplecaptcha -Dversion=1.2.1 -Dpackaging=jar

Ref:
[Using reCAPTCHA with Java/JSP](https://developers.google.com/recaptcha/docs/java)

2. start jetty
`mvn -Dmaven.test.skip=true jetty:run-forked`


3. stop jetty
`mvn -Dmaven.test.skip=true jetty:stop`

