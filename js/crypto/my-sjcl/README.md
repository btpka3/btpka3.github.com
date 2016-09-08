

在考虑API如何认证、授权、签名相关的问题,其中一个想法就是使用公钥秘钥签名加密。
该工程主要就是验证JS的签名、加密结果能否在Java中正确处理。

JavaScript版本的备选加密类库有: [sjcl](https://github.com/bitwiseshiftleft/sjcl)、
[crypto-js](https://github.com/brix/crypto-js/tree/develop/src)。

该工程也顺便学习Grails 3.1

```
grails create-app my-sjcl
cd my-sjcl

./gradlew tasks
grails help
grails list-plugins


grails create-controller me.test.Sjcl
```

在Grails中访问静态资源,需要使用到基于 [asset-pipeline](https://github.com/bertramdev/asset-pipeline)
的grails插件 [grails-asset-pipeline](http://bertramdev.github.io/grails-asset-pipeline/)。

# idea intellij

导入既有的Grails 3工程时,可以直接选中 *.gralde,将其按照gradle工程进行导入。
导入后,在工程名上鼠标右键 - Grails - Configure Grails SDK 来选择Grails SDK的目录。