package me.test.com.tngtech.archunit;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;

import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.CacheMode;
import com.tngtech.archunit.lang.ArchRule;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/12
 */
@AnalyzeClasses(packages = "me.test.archunit.a", cacheMode = CacheMode.PER_CLASS)
public class ATest {

    @ArchTest
    public static final ArchRule disable_use_some_class = classes()
            .should()
            .dependOnClassesThat()
            .areNotAssignableFrom("java.util.concurrent.ThreadPoolExecutor")
            .andShould()
            .dependOnClassesThat()
            .resideInAnyPackage("java.lang");
}
