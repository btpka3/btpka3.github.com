package com.github.btpka3.hello.maven.plugin;

/**
 * @author dangqian.zll
 * @date 2023/9/27
 */
public interface DepApi {



    //
    /**
     * 判断是否依赖。
     *
     * @param dep
     * @param dependOnDep
     * @return 0-无依赖，n - 第n层传递依赖。
     */
    int getDependOnLevel(String dep, String dependOnDep);


}
