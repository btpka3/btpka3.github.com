package me.test.pmd.java.xxx.demo;

import java.util.Collections;
import java.util.List;
import net.sourceforge.pmd.lang.rule.Rule;
import net.sourceforge.pmd.test.PmdRuleTst;

/**
 *
 * @author dangqian.zll
 * @date 2025/8/7
 */
public class MyRuleTest extends PmdRuleTst {

    @Override
    protected List<Rule> getRules() {
        String[] packages = getClass().getPackage().getName().split("\\.");
        String categoryName = packages[packages.length - 1];
        String language = packages[packages.length - 3];
        String rulesetXml = "category/" + language + "/" + categoryName + ".xml";

        Rule rule = findRule(rulesetXml, getClass().getSimpleName().replaceFirst("Test$", ""));
        return Collections.singletonList(rule);
    }
}
