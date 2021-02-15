package me.test;

import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTFieldDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclaration;
import net.sourceforge.pmd.lang.java.ast.ASTMethodDeclarator;
import net.sourceforge.pmd.lang.java.ast.ASTVariableDeclaratorId;
import net.sourceforge.pmd.lang.rule.AbstractRule;

/**
 * @author dangqian.zll
 * @date 2020/12/8
 */
public class ViolationUtils {
    public static void addViolationWithPrecisePosition(AbstractRule rule, Node node, Object data) {
        addViolationWithPrecisePosition(rule, node, data, null);
    }

    public static void addViolationWithPrecisePosition(AbstractRule rule, Node node, Object data,
                                                       String message) {
        if (node instanceof ASTFieldDeclaration) {
            ASTVariableDeclaratorId variableDeclaratorId = node.getFirstDescendantOfType(ASTVariableDeclaratorId.class);
            addViolation(rule, variableDeclaratorId, data, message);
            return;
        }
        if (node instanceof ASTMethodDeclaration) {
            ASTMethodDeclarator declarator = node.getFirstChildOfType(ASTMethodDeclarator.class);
            addViolation(rule, declarator, data, message);
            return;
        }
        addViolation(rule, node, data, message);
    }

    private static void addViolation(AbstractRule rule, Node node, Object data, String message) {
        if (message == null) {
            rule.addViolation(data, node);
        } else {
            rule.addViolationWithMessage(data, node, message);
        }
    }
}
