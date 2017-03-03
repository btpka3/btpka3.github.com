package me.test.groovy.ast

@MapGetterSetter
class FFF extends AbstractForm {

    String desp
    String memo
}

println new FFF().hi_code()
println new FFF().hi_spec()
println new FFF().hi_str()

def f  = new FFF(desp: "aaa", memo: "bbb")
f.setMemo("bbb1")
println f


class DDD extends AbstractForm {

    String a
    String b

    void setA(String a){
        println "====== a : "+a
        this.a = a
    }

    String getA(){return a}
    void setB(String b){
        println "====== b : "+b
        this.b = b
    }
    String getB(){return b}
}

new DDD(a:"aaa",b:"bbb")