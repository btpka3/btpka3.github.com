package com.github.btpka3.first.spring.boot4;

import com.tngtech.archunit.base.DescribedPredicate;
import com.tngtech.archunit.core.domain.JavaClass;
import com.tngtech.archunit.core.domain.JavaMethodCall;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.junit.CacheMode;
import com.tngtech.archunit.lang.ArchCondition;
import com.tngtech.archunit.lang.ArchRule;
import com.tngtech.archunit.lang.ConditionEvents;
import com.tngtech.archunit.lang.SimpleConditionEvent;
import com.tngtech.archunit.library.DependencyRules;
import com.tngtech.archunit.library.GeneralCodingRules;
import com.tngtech.archunit.library.freeze.FreezingArchRule;
import org.springframework.security.access.annotation.Secured;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static com.tngtech.archunit.library.dependencies.SlicesRuleDefinition.slices;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/12
 */
@AnalyzeClasses(
        packages = "com.mycompany.myapp",
        cacheMode = CacheMode.PER_CLASS
)
public class MyArchUnitTest {

    /**
     * 包 foo 不允许依赖包 bar。
     */
    @ArchTest
    public static final ArchRule rule1 = noClasses()
            .that().resideInAPackage("..foo..")
            .should().onlyBeAccessed().byAnyPackage("..bar..");
    /**
     * 包 foo 只能使用包 bar。
     */
    @ArchTest
    public static final ArchRule rule3 = classes()
            .that().resideInAPackage("..foo..")
            .should().dependOnClassesThat().resideInAPackage("..bar..")
            .because("xxx msg");

    /**
     * 包 foo 只能被包 bar 使用。
     */
    @ArchTest
    public static final ArchRule rule2 = classes()
            .that().resideInAPackage("..foo..")
            .should().onlyHaveDependentClassesThat().resideInAnyPackage("..bar..")
            .because("xxx msg");

    /**
     * Dao 类 只能被 Service 类使用。
     */
    @ArchTest
    public static final ArchRule rule23 = classes()
            .that().haveNameMatching(".*Dao")
            .should().onlyHaveDependentClassesThat().haveSimpleName(".*Service")
            .because("xxx msg");

    /**
     * 包 com.myapp 下的 直属子包间不能存在循环依赖
     */
    @ArchTest
    public static final ArchRule rule24 = slices()
            .matching("com.myapp.(*)..").should().beFreeOfCycles()
            .as("xxx msg");


    static DescribedPredicate<JavaClass> haveAFieldAnnotatedWithPayload = new DescribedPredicate<>("have a field annotated with @Payload") {
        @Override
        public boolean test(JavaClass javaClass) {
            return false;
        }
    };
    static ArchCondition<JavaClass> onlyBeAccessedBySecuredMethods = new ArchCondition<>("only be accessed by @Secured methods") {
        @Override
        public void check(JavaClass item, ConditionEvents events) {
            for (JavaMethodCall call : item.getMethodCallsToSelf()) {
                if (!call.getOrigin().isAnnotatedWith(Secured.class)) {
                    String message = String.format("Method %s is not @Secured", call.getOrigin().getFullName());
                    events.add(SimpleConditionEvent.violated(call, message));
                }
            }
        }
    };

    @ArchTest
    public static final ArchRule rule25 = classes()
            .that(haveAFieldAnnotatedWithPayload)
            .should(DependencyRules.dependOnUpperPackages().and(onlyBeAccessedBySecuredMethods))
            .as("xxx msg");

    @ArchTest
    public static final ArchRule r = GeneralCodingRules.testClassesShouldResideInTheSamePackageAsImplementation();

    /**
     * 冻结规则，暂停使用。
     */
    @ArchTest
    public static final ArchRule rule = FreezingArchRule.freeze(classes().should(DependencyRules.dependOnUpperPackages()));

}
