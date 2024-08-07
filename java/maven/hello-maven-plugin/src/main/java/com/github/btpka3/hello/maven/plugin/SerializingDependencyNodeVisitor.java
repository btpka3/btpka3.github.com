package com.github.btpka3.hello.maven.plugin;

import org.eclipse.aether.graph.DependencyNode;
import org.eclipse.aether.graph.DependencyVisitor;

import java.io.PrintWriter;
import java.io.Writer;
import java.util.List;

/**
 * @author dangqian.zll
 * @date 2023/10/7
 */
public class SerializingDependencyNodeVisitor implements DependencyVisitor {
    @Override
    public boolean visitEnter(DependencyNode node) {
        return visit(node);
    }

    @Override
    public boolean visitLeave(DependencyNode node) {
        return endVisit(node);
    }
    // classes ----------------------------------------------------------------

    /**
     * Provides tokens to use when serializing the dependency graph.
     */
    public static class GraphTokens {
        private final String nodeIndent;

        private final String lastNodeIndent;

        private final String fillIndent;

        private final String lastFillIndent;

        public GraphTokens(String nodeIndent, String lastNodeIndent, String fillIndent, String lastFillIndent) {
            this.nodeIndent = nodeIndent;
            this.lastNodeIndent = lastNodeIndent;
            this.fillIndent = fillIndent;
            this.lastFillIndent = lastFillIndent;
        }

        public String getNodeIndent(boolean last) {
            return last ? lastNodeIndent : nodeIndent;
        }

        public String getFillIndent(boolean last) {
            return last ? lastFillIndent : fillIndent;
        }
    }

    // constants --------------------------------------------------------------

    /**
     * Whitespace tokens to use when outputing the dependency graph.
     */
    public static final GraphTokens WHITESPACE_TOKENS = new GraphTokens("   ", "   ", "   ", "   ");

    /**
     * The standard ASCII tokens to use when outputing the dependency graph.
     */
    public static final GraphTokens STANDARD_TOKENS = new GraphTokens("+- ", "\\- ", "|  ", "   ");

    /**
     * The extended ASCII tokens to use when outputing the dependency graph.
     */
    public static final GraphTokens EXTENDED_TOKENS = new GraphTokens("\u251C\u2500 ", "\u2514\u2500 ", "\u2502  ",
            "   ");

    // fields -----------------------------------------------------------------

    /**
     * The writer to serialize to.
     */
    private final PrintWriter writer;

    /**
     * The tokens to use when serializing the dependency graph.
     */
    private final GraphTokens tokens;

    /**
     * The depth of the currently visited dependency node.
     */
    private int depth;

    // constructors -----------------------------------------------------------

    /**
     * Creates a dependency node visitor that serializes visited nodes to the specified writer using whitespace tokens.
     *
     * @param writer the writer to serialize to
     */
    public SerializingDependencyNodeVisitor(Writer writer) {
        this(writer, WHITESPACE_TOKENS);
    }

    /**
     * Creates a dependency node visitor that serializes visited nodes to the specified writer using the specified
     * tokens.
     *
     * @param writer the writer to serialize to
     * @param tokens the tokens to use when serializing the dependency graph
     */
    public SerializingDependencyNodeVisitor(Writer writer, GraphTokens tokens) {
        if (writer instanceof PrintWriter) {
            this.writer = (PrintWriter) writer;
        } else {
            this.writer = new PrintWriter(writer, true);
        }

        this.tokens = tokens;

        depth = 0;
    }

    // DependencyNodeVisitor methods ------------------------------------------

    /**
     * {@inheritDoc}
     */
    public boolean visit(DependencyNode node) {
        indent(node);

        writer.println(node.getArtifact());

        depth++;

        return true;
    }

    /**
     * {@inheritDoc}
     */
    public boolean endVisit(DependencyNode node) {
        depth--;

        return true;
    }

    // private methods --------------------------------------------------------

    /**
     * Writes the necessary tokens to indent the specified dependency node to this visitor's writer.
     *
     * @param node the dependency node to indent
     */
    private void indent(DependencyNode node) {
        for (int i = 1; i < depth; i++) {
            writer.write(tokens.getFillIndent(isLast(node, i)));
        }

        if (depth > 0) {
            writer.write(tokens.getNodeIndent(isLast(node)));
        }
    }

    /**
     * Gets whether the specified dependency node is the last of its siblings.
     *
     * @param node the dependency node to check
     * @return <code>true</code> if the specified dependency node is the last of its last siblings
     */
    private boolean isLast(DependencyNode node) {
        // TODO: remove node argument and calculate from visitor calls only

        DependencyNode parent = ((DependencyNodeEx) node).getParent();

        boolean last;

        if (parent == null) {
            last = true;
        } else {
            List<DependencyNode> siblings = parent.getChildren();

            last = (siblings.indexOf(node) == siblings.size() - 1);
        }

        return last;
    }

    /**
     * Gets whether the specified dependency node ancestor is the last of its siblings.
     *
     * @param node          the dependency node whose ancestor to check
     * @param ancestorDepth the depth of the ancestor of the specified dependency node to check
     * @return <code>true</code> if the specified dependency node ancestor is the last of its siblings
     */
    private boolean isLast(DependencyNode node, int ancestorDepth) {
        // TODO: remove node argument and calculate from visitor calls only

        int distance = depth - ancestorDepth;

        while (distance-- > 0) {
            node = ((DependencyNodeEx) node).getParent();
        }

        return isLast(node);
    }
}