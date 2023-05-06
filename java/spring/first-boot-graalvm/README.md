- spring boot : [GraalVM Native Image Support](https://docs.spring.io/spring-boot/docs/current/reference/html/native-image.html)
- [pack CLI](https://buildpacks.io/docs/tools/pack/)
- [Setting up GraalVM with Native Image Support](https://graalvm.github.io/native-build-tools/latest/graalvm-setup.html)
- [Gradle plugin for GraalVM Native Image building](https://graalvm.github.io/native-build-tools/latest/gradle-plugin.html)
- [Spring Boot Gradle Plugin Reference Guide](https://docs.spring.io/spring-boot/docs/3.0.3/gradle-plugin/reference/htmlsingle/)
- [GraalVM Community Images](https://www.graalvm.org/latest/docs/getting-started/container-images/)
- [GraalVM Container](https://github.com/search?q=repo%3Agraalvm%2Fcontainer++&type=registrypackages)

# Building a Native Image Using Buildpacks

```bash
sdk install java 22.3.r19-grl

# 前提：需安装 docker 和 pack
# 构建
./gradlew bootBuildImage

# 运行 (该镜像97M)
docker run -d -p 8080:8080 --name first-boot-graalvm first-boot-graalvm:0.0.1-SNAPSHOT



```

# Building a Native Image using Native Build Tools

```bash

# 构建
./gradlew nativeCompile

# 运行 (该文件 66M)
./build/native/nativeCompile/first-boot-graalvm   

# 验证
curl -v 'http://localhost:8080/test/add?a=1&b=2'
```