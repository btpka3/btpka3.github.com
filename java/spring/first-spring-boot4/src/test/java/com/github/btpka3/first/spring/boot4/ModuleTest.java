package com.github.btpka3.first.spring.boot4;

import com.tngtech.archunit.core.domain.JavaClass;
import org.springframework.modulith.core.ApplicationModule;
import org.springframework.modulith.core.ApplicationModules;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/11
 */
public class ModuleTest {

    /**
     * @see org.springframework.modulith.ApplicationModule#allowedDependencies()
     */
    public void x() {
        ApplicationModules modules = ApplicationModules.of(FirstSpringBoot4Application.class);
        org.springframework.modulith.ApplicationModule mAnn;
        ApplicationModule m;
        modules.verify();


        ApplicationModules.of(
                FirstSpringBoot4Application.class,
                JavaClass.Predicates.resideInAPackage("com.example.db")).verify();
    }
}
