# 参考

- [Java 9 Migration Guide: The Seven Most Common Challenges](https://blog.codefx.org/java/java-9-migration-guide/)
- [Making JSR 305 Work On Java 9](https://blog.codefx.org/java/jsr-305-java-9/)

```bash
mvn -Dmaven.test.skip=true clean package
mvn -Dmaven.test.skip=true clean verify 
mvn clean javafx:run -pl hello


# 代码格式化:检查, 可通过 -Dspotless.check.skip=true 跳过
mvn spotless:check
# 代码格式化:启用
mvn spotless:apply
# 本地启用 git hook, git push 前，会先check,再apply,并中断git push,给出错误提示，你只需git diff 确认后再 commit,push.
mvn spotless:install-git-pre-push-hook
```