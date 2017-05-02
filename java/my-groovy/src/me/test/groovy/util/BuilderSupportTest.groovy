package me.test.groovy.util

/**
 *
 * http://docs.groovy-lang.org/latest/html/documentation/type-checking-extensions.html
 */
class BuilderSupportTest extends BuilderSupport {

    static void main(String[] args) {
        println "1111"
        BuilderSupportTest b = new BuilderSupportTest()
        println b.echo(a: "aa")
    }

    protected void nodeCompleted(Object parent, Object node) {
        println "----------nodeCompleted : parent=${parent}, node=${node}"
    }

    @Override
    protected void setParent(Object parent, Object child) {

    }

    @Override
    protected Object createNode(Object name) {
        return [
                name: name
        ]
    }

    @Override
    protected Object createNode(Object name, Object value) {
        return [
                name: name,
                txt : value
        ]

    }

    @Override
    protected Object createNode(Object name, Map attributes) {
        return [
                name: name,
                attr: attributes
        ]
    }

    @Override
    protected Object createNode(Object name, Map attributes, Object value) {
        return [
                name: name,
                txt : value,
                attr: attributes
        ]
    }
}
