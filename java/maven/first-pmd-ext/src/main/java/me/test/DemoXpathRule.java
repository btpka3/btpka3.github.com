package me.test;


import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.rule.XPathRule;

/**
 * [Mandatory] Never use return within a finally block.
 * A return statement in a finally block will cause exception or result
 * in a discarded return value in the try-catch block.
 *
 * @author zenghou.fw
 * @date 2017/03/29
 */
public class DemoXpathRule extends XPathRule {
    private static final String XPATH = "//FinallyStatement//ReturnStatement";

    @Override
    public void addViolation(Object data, Node node, String arg) {
        ViolationUtils.addViolationWithPrecisePosition(this, node, data,
                "java.exception.AvoidReturnInFinallyRule.violation.msg");
    }
}