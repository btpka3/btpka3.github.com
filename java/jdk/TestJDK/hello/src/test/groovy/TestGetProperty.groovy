// 执行： groovy TestGetProperty.groovy

class MyPojo {

    String getAaa() {
        return "aaa"
    }

    Object getProperty(String property) {

        if (property == "metaClass") {
            return metaClass;
        }

        return "property_" + property + "_value";
    }
}

def myPojo = new MyPojo();
assert myPojo.aaa == "property_aaa_value"
assert myPojo.getAaa() == "aaa"
// property_bbb_value
println "myPojo.bbb=" + myPojo.bbb
// property_ddd_value
println "myPojo.getProperty(\"ddd\")=" + myPojo.getProperty("ddd")
// property_metaClass_value
println myPojo.metaClass


class MyPojo1 {
    def aaa = 'aaa01'
    def bbb = 'bbb01'


    String getAaa() {
        return "getAaa01"
    }

}

MyPojo1 myPojo1 = new MyPojo1();
// getAaa01
println "myPojo1.aaa=" + myPojo1.aaa
// getAaa01
println "myPojo1.getAaa()=" + myPojo1.getAaa()
// aaa01
println "myPojo1.@aaa=" + myPojo1.@aaa
// bbb01
println "myPojo1.bbb=" + myPojo1.bbb

// aaa01
println "myPojo1.metaClass.getAttribute(myPojo1, 'aaa')=" + myPojo1.metaClass.getAttribute(myPojo1, 'aaa')

