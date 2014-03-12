
package me.test.xml;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class MyPojoAspectLogic {

    private Map configMap;

    public void printBefore() {

        System.out.println("111111111 - xml :" + configMap);
    }

    public void printAfter() {

        System.out.println("222222222 - xml :" + configMap);
    }

    public void setConfigMap(Map configMap) {

        this.configMap = configMap;
    }

}
