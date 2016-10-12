package me.test
/**
 * 查找方法先后顺序, 比如 xxxObj.xxxMethod()
 *
 * 1.0 MyClass.metaClass.invokeMethod : hi([])
 * 2.0  谁最后定义,谁最先执行
 * 2.1 MyClass.metaClass.static.hi()
 * 2.2 MyClass.metaClass.hi()
 * 2.3 MyClass.mixin : MyExpand#hi()
 * 3.0 MyClass#hi()
 * 6. MyClass#methodMissing : hi([])
 * 7. MyClass#invokeMethod : hi([])
 *
 * 参考:
 *
 * 1. http://www.groovy-lang.org/metaprogramming.html#_metaclasses
 * 2. http://docs.groovy-lang.org/latest/html/gapi/groovy/lang/ExpandoMetaClass.html
 *
 */
class Metaprogramming02 {


    String hi() {
        "3.0 MyClass#hi()"
    }

    def methodMissing(String name, def args) {
        "5.0 MyClass#methodMissing : $name($args)"
    }

    def invokeMethod(String name, Object args) {
        "6.0 MyClass#invokeMethod : $name($args)"
    }

    static void main(String[] args) {

        // 内部应当使用 MetaClass#getMetaMethod() MetaClass#hasMetaMethod()
        Metaprogramming02.metaClass.invokeMethod = { String name, _args ->
            return "1.0 MyClass.metaClass.invokeMethod : $name($_args)"
        }

        Metaprogramming02.metaClass.'static'.hi << {
            "2.1 MyClass.metaClass.'static'.hi()"
        }
        Metaprogramming02.metaClass.hi = {
            "2.2 MyClass.metaClass.hi()"
        }
        Metaprogramming02.mixin MyExpand


        Metaprogramming02.metaClass.methodMissing = { String name, def _args ->
            "4.0 MyClass.metaClass.methodMissing : $name($_args)"
        }


        // ====================== 测试
        def obj = new Metaprogramming02()
        println   obj.hi();



//        // -------------- 补充
//        // XXX: 难以理解, 务必慎用。
//        Metaprogramming02.metaClass.'static'.invokeMethod << { String name, _args ->
//            def metaMethod = Metaprogramming02.metaClass.getStaticMetaMethod(name, args)
//            def result
//            if (metaMethod) {
//                result = metaMethod.invoke(delegate, args)
//                println "========$name($_args)"
//                return result
//            } else {
//                result = "bar"
//            }
//            result
//        }
//        Metaprogramming02.metaClass.'static'.methodMissing << { String name, _args ->
//            "MyClass.metaClass.'static'.methodMissing() : $name($_args)"
//        }
    }
}

class MyExpand {

    String hi() {
        "2.3 MyClass.mixin : MyExpand#hi()"
    }
}
