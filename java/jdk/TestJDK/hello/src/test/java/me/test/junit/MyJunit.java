package me.test.junit;

import static org.junit.platform.engine.discovery.ClassNameFilter.includeClassNamePatterns;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectPackage;
import static org.junit.platform.launcher.core.LauncherDiscoveryRequestBuilder.discoveryRequest;

import java.io.PrintWriter;
import org.junit.platform.console.options.ConsoleUtils;
import org.junit.platform.console.output.ColorPalette;
import org.junit.platform.console.output.DetailsPrintingListener;
import org.junit.platform.console.output.Theme;
import org.junit.platform.console.output.TreePrintingListener;
import org.junit.platform.console.output.VerboseTreePrintingListener;
import org.junit.platform.launcher.Launcher;
import org.junit.platform.launcher.LauncherDiscoveryRequest;
import org.junit.platform.launcher.LauncherExecutionRequest;
import org.junit.platform.launcher.LauncherSession;
import org.junit.platform.launcher.TestPlan;
import org.junit.platform.launcher.core.LauncherExecutionRequestBuilder;
import org.junit.platform.launcher.core.LauncherFactory;
import org.junit.platform.launcher.listeners.SummaryGeneratingListener;
import org.junit.platform.launcher.listeners.TestExecutionSummary;
import org.junit.platform.reporting.open.xml.OpenTestReportGeneratingListener;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/16
 */
public class MyJunit {

    public static void main(String[] args) {

        // 创建 Launcher
        Launcher launcher = LauncherFactory.create();

        // 创建监听器（用于收集测试结果）
        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        DetailsPrintingListener detailsPrintingListener = new TreePrintingListener(
                new PrintWriter(System.out), ColorPalette.DEFAULT, Theme.valueOf(ConsoleUtils.charset()));
        DetailsPrintingListener verboseTreePrintingListener = new VerboseTreePrintingListener(
                new PrintWriter(System.out), ColorPalette.DEFAULT, 16, Theme.valueOf(ConsoleUtils.charset()));
        // 请查看 ./target/open-test-report.xml
        // java -jar
        // ${HOME}/.m2/repository/org/opentest4j/reporting/open-test-reporting-cli/0.2.3/open-test-reporting-cli-0.2.3-standalone.jar convert target/open-test-report.xml
        // ls -l target/hierarchy.xml
        // java -jar
        // ${HOME}/.m2/repository/org/opentest4j/reporting/open-test-reporting-cli/0.2.3/open-test-reporting-cli-0.2.3-standalone.jar html-report --output target/open-test-report.html target/open-test-report.xml
        // @see org.opentest4j.reporting.cli.ReportingCli
        // @see org.opentest4j.reporting.cli.HtmlReportCommand
        // @see new DefaultHtmlReportWriter().writeHtmlReport(xmlFiles, targetFile);
        OpenTestReportGeneratingListener openTestReportGeneratingListener = new OpenTestReportGeneratingListener();

        // 发现并选择测试用例
        // ClassSelector selectors = DiscoverySelectors.selectClass(HelloTest.class);
        // 或通过正则表达式匹配（例如所有以 "MyIntegrationTest" 开头的类）：
        // selectors = DiscoverySelectors.selectories()
        //     .withFilters(new ClassNamePatternFilter(".*MyIntegrationTest.*"));

        LauncherDiscoveryRequest discoveryRequest = discoveryRequest()
                // @see OpenTestReportGeneratingListener
                .configurationParameter("junit.platform.reporting.open.xml.enabled", "true")
                .configurationParameter("junit.platform.reporting.open.xml.git.enabled", "true")
                .selectors(
                        //                        selectPackage("org.example.user"),
                        //                        selectClass("org.example.payment.PaymentTests"),
                        selectClass(HelloTest.class)
                        //                        selectMethod("org.example.order.OrderTests#test1"),
                        //                        selectMethod("org.example.order.OrderTests#test2()"),
                        //                        selectMethod("org.example.order.OrderTests#test3(java.lang.String)"),
                        //                        selectMethod("org.example.order.OrderTests", "test4"),
                        //                        selectMethod(OrderTests.class, "test5"),
                        //                        selectMethod(OrderTests.class, testMethod),
                        //                        selectUniqueId("unique-id-1"),
                        //                        selectUniqueId("unique-id-2")
                        )
                .build();

        LauncherExecutionRequest executionRequest = LauncherExecutionRequestBuilder.request(discoveryRequest)
                .listeners(
                        //                        verboseTreePrintingListener,
                        detailsPrintingListener, listener)
                .build();

        // 执行测试
        launcher.execute(executionRequest);

        // 获取执行摘要
        TestExecutionSummary summary = listener.getSummary();
        System.out.println("测试结果：");
        summary.printTo(new PrintWriter(System.out));
    }

    public void x() {
        LauncherDiscoveryRequest discoveryRequest = discoveryRequest()
                .selectors(selectPackage("com.example.mytests"), selectClass(HelloTest.class))
                .filters(includeClassNamePatterns(".*Tests"))
                .build();

        SummaryGeneratingListener listener = new SummaryGeneratingListener();
        try (LauncherSession session = LauncherFactory.openSession()) {

            Launcher launcher = session.getLauncher();
            // Register one ore more listeners of your choice.
            launcher.registerTestExecutionListeners(listener);
            // Discover tests and build a test plan.
            TestPlan testPlan = launcher.discover(discoveryRequest);
            // Execute the test plan.
            launcher.execute(testPlan);
            // Alternatively, execute the discovery request directly.
            launcher.execute(discoveryRequest);
        }
        TestExecutionSummary summary = listener.getSummary();
    }
}
