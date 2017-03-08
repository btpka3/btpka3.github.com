该模块用以尝试使用 groovy 写domain bean，并使用querydsl 生成query bean。

问题： 

```log
2:43:39 PM: Executing external task 'build'...
:data-domain-groovy:compileJava UP-TO-DATE
Note: Running MongoAnnotationProcessor
Note: Running MongoAnnotationProcessor
Note: Serializing Entity types
Note: Generating me.test.first.spring.boot.data.mongo.domain.QGroovyUser for [me.test.first.spring.boot.data.mongo.domain.GroovyUser]
Note: Serializing Embeddable types
Note: Generating groovy.lang.QMetaClass for [groovy.lang.MetaClass]
Note: Generating org.bson.types.QObjectId for [org.bson.types.ObjectId]
Note: Running MongoAnnotationProcessor
/Users/zll/work/git-repo/github/btpka3/btpka3.github.com/java/spring/first-spring-boot-data-mongo/data-domain-groovy/src/main/generated/groovy/lang/QMetaClass.java:23: error: cannot find symbol
    public final QMetaObjectProtocol _super = new QMetaObjectProtocol(this);
                 ^
  symbol:   class QMetaObjectProtocol
  location: class QMetaClass
Note: Running MongoAnnotationProcessor
1 error
startup failed:
Compilation failed; see the compiler error output for details.

1 error

:data-domain-groovy:generateQueryDSL FAILED

FAILURE: Build failed with an exception.

* What went wrong:
Execution failed for task ':data-domain-groovy:generateQueryDSL'.
> Compilation failed; see the compiler error output for details.

* Try:
Run with --stacktrace option to get the stack trace. Run with --info or --debug option to get more log output.

BUILD FAILED

Total time: 6.108 secs
Compilation failed; see the compiler error output for details.
2:43:45 PM: External task execution finished 'build'.

```