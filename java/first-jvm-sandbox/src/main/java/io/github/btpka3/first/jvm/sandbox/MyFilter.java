package com.alibaba.security.gong9.sandbox.tools.sandbox;

import com.alibaba.jvm.sandbox.api.filter.Filter;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

/**
 * @author dangqian.zll
 * @date 2024/10/21
 * @see com.alibaba.security.mtee.bundle.framework.core.loader.JiugongBundleClassLoader#loadClass(String, boolean)
 * @see com.alibaba.security.mtee.bundle.framework.core.loader.DefaultBundleClassLoader#loadClass(String, boolean)
 */
public class MyFilter implements Filter {

    protected static Set<String> classNameSet = new HashSet<>(Arrays.asList(
            "com.alibaba.security.mtee.bundle.framework.core.loader.JiugongBundleClassLoader",
            "com.alibaba.security.mtee.bundle.framework.core.loader.DefaultBundleClassLoader"
    ));
    protected static Set<String> methodNameSet = new HashSet<>(Arrays.asList(
            "loadClass"
//            ,
//            "loadFromBundleFramework",
//            "loadFromBundleExtension",
//            "loadFromBizShare",
//            "loadFromBiz"
    ));

    @Override
    public boolean doClassFilter(int access, String javaClassName, String superClassTypeJavaClassName, String[] interfaceTypeJavaClassNameArray, String[] annotationTypeJavaClassNameArray) {
        return classNameSet.contains(javaClassName);
    }

    @Override
    public boolean doMethodFilter(int access, String javaMethodName, String[] parameterTypeJavaClassNameArray, String[] throwsTypeJavaClassNameArray, String[] annotationTypeJavaClassNameArray) {
        return methodNameSet.contains(javaMethodName);
    }
}
