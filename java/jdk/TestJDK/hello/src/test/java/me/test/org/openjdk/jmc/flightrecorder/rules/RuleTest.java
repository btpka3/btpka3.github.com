package me.test.org.openjdk.jmc.flightrecorder.rules;

import java.io.File;
import java.util.concurrent.RunnableFuture;
import org.openjdk.jmc.common.item.IItemCollection;
import org.openjdk.jmc.common.util.IPreferenceValueProvider;
import org.openjdk.jmc.flightrecorder.JfrLoaderToolkit;
import org.openjdk.jmc.flightrecorder.rules.IResult;
import org.openjdk.jmc.flightrecorder.rules.IResultValueProvider;
import org.openjdk.jmc.flightrecorder.rules.IRule;
import org.openjdk.jmc.flightrecorder.rules.RuleRegistry;

/**
 *
 * @author dangqian.zll
 * @date 2026/6/29
 * @see <a href="https://github.com/openjdk/jmc>jmc</a>
 */
public class RuleTest {
    public static void main(String[] args) throws Exception {
        File recording = null;
        IItemCollection events = JfrLoaderToolkit.loadEvents(recording);
        IResultValueProvider dependencyResults = null;
        for (IRule rule : RuleRegistry.getRules()) {
            RunnableFuture<IResult> future = rule.createEvaluation(events, IPreferenceValueProvider.DEFAULT_VALUES, dependencyResults);
            future.run();
            IResult result = future.get();
//            if (result.getScore() > 50) {
//                System.out.println(String.format("[Score: %3.0f] Rule ID: %s, Rule name: %s, Short description: %s",
//                        result.getScore(), result.getRule().getId(), result.getRule().getName(),
//                        result.getShortDescription()));
//            }
        }
    }
}
