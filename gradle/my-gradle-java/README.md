

## GAV

gradle.properties

```
theGroup=some.group
theName=someName
theVersion=1.0
theSourceCompatibility=1.6
```

settings.gradle

```
rootProject.name = theName
```


build.gradle

```
apply plugin: "java"

group = theGroup
version = theVersion
sourceCompatibility = theSourceCompatibility
```


`apply from: "other.gradle"`



## Gradle 执行main函数

```
apply plugin:'application'
mainClassName = System.getProperty("mainClassName")
```

命令行执行 
```
gradle -DmainClassName="me.test.BBB" :sub-module-b:run -q
```

