# 参考

* [Annotation Processing 101](http://hannesdorfmann.com/annotation-processing/annotationprocessing101)
* [source](https://github.com/sockeqwe/annotationprocessing101)

# run

```bash
cd TestJDK
mvn -Dmaven.test.skip=true --projects apt install
mvn -Dmaven.test.skip=true --projects hello package
```

# 总结

1. maven-compiler-plugin 可以通过参数指定要使用哪些 annotationProcessor

```xml

<plugins>
    <plugin>
        <groupId>org.apache.maven.plugins</groupId>
        <artifactId>maven-compiler-plugin</artifactId>
        <version>3.0</version>
        <configuration>
            <annotationProcessors>
                <annotationProcessor>org.springframework.data.mongodb.repository.support.MongoAnnotationProcessor
                </annotationProcessor>
            </annotationProcessors>
            <source>1.7</source>
            <target>1.7</target>
        </configuration>
    </plugin>
</plugins>
```

1. gradle

```groovy
configurations {
    querydslapt
}

dependencies {
    // put dependency to your module with processor inside
    querydslapt "com.mysema.querydsl:querydsl-apt:$querydslVersion"
}

task generateQueryDSL(type: JavaCompile, group: 'build', description: 'Generates the QueryDSL query types') {
    source = sourceSets.main.java   // input source set
    classpath = configurations.compile + configurations.querydslapt // add processor module to classpath
    // specify javac arguments
    options.compilerArgs = [
            "-proc:only",
            "-processor", "com.mysema.query.apt.jpa.JPAAnnotationProcessor" // your processor here
    ]
    // specify output of generated code
    destinationDir = sourceSets.generated.java.srcDirs.iterator().next()
}
```

# FIXME

1. `META-INF/services/javax.annotation.processing.Processor` 应该是放在哪个jar包中？

    javax.annotation.processing.Processor
    
    ```txt
    me.test.jdk.javax.annotation.processing.MyProcessor
    ```

1. gradle 中能够在同一个工程中同时使用 querydsl 生成的内容？