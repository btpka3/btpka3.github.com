package com.github.btpka3.first.spring.boot4;

import com.tngtech.archunit.core.domain.JavaClass;
import org.springframework.modulith.Modulith;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/11
 */
// @EnableModulith
 @Modulith
// @ApplicationModule
public class ModuleTest {

    /**
     * @see org.springframework.modulith.ApplicationModule#allowedDependencies()
     */
    public void x() {
        ApplicationModules modules = ApplicationModules.of(
                FirstSpringBoot4Application.class,
                // 要 excluded/排除/忽略的 的java package
                JavaClass.Predicates.resideInAPackage("com.example.db")
                //PackageMatcher.of("..pack..")
        );
        ApplicationModule mAnn;
        ApplicationModule m;
        modules.verify();

        ApplicationModules.of(
                FirstSpringBoot4Application.class,
                JavaClass.Predicates.resideInAPackage("com.example.db")).verify();
    }
}
