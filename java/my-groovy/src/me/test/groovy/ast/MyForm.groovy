package me.test.groovy.ast

@MapGetterSetter
class MyForm extends AbstractForm {

    String name
    String addr

    static void main(String[] args) {
        MyForm myForm = new MyForm(name: "zhang3", addr: "li4")
        println "myForm.toString() = " + myForm.toString()
        println "myForm.hi_code() = " + myForm.hi_code()
        println "myForm.hi_spec() = " + myForm.hi_spec()
        println "myForm.hi_str() = " + myForm.hi_str()

        myForm.setAddr("wang5")
        println "myForm.toString() = " + myForm.toString()
    }
}
