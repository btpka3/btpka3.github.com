package me.test

// GroovyObject
class Metaprogramming01 {

    def field0 = 'f00000'

    def storage = [:]


    static void main(String[] args) {

        def obj = new Metaprogramming01();

        // 对象上的动态方法
        println "obj.hi(1, 2, 3) = " + obj.hi(1, 2, 3)

        // 对象上的动态属性
        println "obj.field0 = " + obj.field0
        println "obj.field1 = " + obj.field1
        println "obj.field2 = " + obj.field2
        obj.field3 = "999"
        println "obj.field3 = " + obj.field3


    }

    // 实例级别的
    def invokeMethod(String name, Object args) {
        return "obj.invokeMethod : $name($args)"
    }

    // 2.1 有限处理特定额外的属性
    def getProperty(String name) {
        println("=========getProperty($name)")
        if (name != 'field1')
            return metaClass.getProperty(this, name)
        else
            return "field[$name]"
    }

    // 2.2 再额外补充缺失的属性
    def propertyMissing(String name, value) {
        println("=========propertyMissing($name, $value)")
        storage[name] = value
    }

    def propertyMissing(String name) {
        println("=========propertyMissing($name)")
        storage[name]
    }


}
