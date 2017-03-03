package me.test.groovy.ast


abstract class AbstractForm {

    protected final Map<String, String> map = new LinkedHashMap ( )

    @Override
    String toString(){
        return this.map.toString()
    }
}
