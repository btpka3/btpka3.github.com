package me.test.junit;

import static java.util.Collections.emptyList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.platform.engine.discovery.DiscoverySelectors.selectClass;

import org.junit.jupiter.engine.descriptor.JupiterEngineDescriptor;
import org.junit.platform.testkit.engine.EngineDiscoveryResults;
import org.junit.platform.testkit.engine.EngineTestKit;

/**
 *
 * @author dangqian.zll
 * @date 2025/12/17
 */
public class MyTestPlanDemo {
    public static void main(String[] args) {

        EngineTestKit.engine(JupiterEngineDescriptor.ENGINE_ID)
                .selectors(selectClass(HelloTest.class))
                .configurationParameter("junit.platform.reporting.open.xml.enabled", "true")
                .configurationParameter("junit.platform.reporting.open.xml.git.enabled", "true")
                .execute()
                .containerEvents()
                .assertStatistics(stats -> stats.started(2).succeeded(1));
    }

    public void discoverDemo() {
        EngineDiscoveryResults results = EngineTestKit.engine("junit-jupiter")
                .selectors(selectClass(HelloTest.class))
                .discover();

        assertEquals("JUnit Jupiter", results.getEngineDescriptor().getDisplayName());
        assertEquals(emptyList(), results.getDiscoveryIssues());
    }
}
