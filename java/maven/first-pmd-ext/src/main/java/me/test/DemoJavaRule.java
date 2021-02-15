package me.test;

import net.sourceforge.pmd.RuleContext;
import net.sourceforge.pmd.lang.ast.Node;
import net.sourceforge.pmd.lang.java.ast.ASTCompilationUnit;
import net.sourceforge.pmd.lang.java.rule.AbstractJavaRule;
import org.apache.commons.lang3.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author dangqian.zll
 * @date 2020/12/8
 */

public class DemoJavaRule extends AbstractJavaRule {

    private static final Map<String, Boolean> TYPE_RESOLVER_MAP = new ConcurrentHashMap<>(16);

    private static final String EMPTY_FILE_NAME = "n/a";
    private static final String DELIMITER = "-";

    @Override
    public Object visit(ASTCompilationUnit node, Object data) {
        // Each CompilationUnit will be scanned only once by custom type resolver.
        String sourceCodeFilename = ((RuleContext) data).getSourceCodeFilename();

        // Do type resolve if file name is empty(unit tests).
        if (StringUtils.isBlank(sourceCodeFilename) || EMPTY_FILE_NAME.equals(sourceCodeFilename)) {
            resolveType(node, data);
            return super.visit(node, data);
        }

        // If file name is not empty, use filename + hashcode to identify a compilation unit.
        String uniqueId = sourceCodeFilename + DELIMITER + node.hashCode();
        if (!TYPE_RESOLVER_MAP.containsKey(uniqueId)) {
            resolveType(node, data);
            TYPE_RESOLVER_MAP.put(uniqueId, true);
        }
        return super.visit(node, data);
    }

    @Override
    public void setDescription(String description) {
        super.setDescription( description);
    }

    @Override
    public void setMessage(String message) {
        super.setMessage(message);
    }

    @Override
    public void addViolationWithMessage(Object data, Node node, String message) {
        super.addViolationWithMessage(data, node, message);
    }

    @Override
    public void addViolationWithMessage(Object data, Node node, String message, Object[] args) {
        super.addViolationWithMessage(data, node,
                String.format(message, args));
    }

    private void resolveType(ASTCompilationUnit node, Object data) {
//        FixClassTypeResolver classTypeResolver = new FixClassTypeResolver(AbstractAliRule.class.getClassLoader());
//        node.setClassTypeResolver(classTypeResolver);
//        node.jjtAccept(classTypeResolver, data);
    }
}