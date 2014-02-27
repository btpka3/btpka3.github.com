
package me.test.xml;

import java.util.Map;

@SuppressWarnings("rawtypes")
public class MyAspectLogic {

    private Map cfgMap;

    public void printBefore() {

        System.out.println("111111111111 :" + cfgMap);
    }

    public void printAfter() {

        System.out.println("222222222222 :" + cfgMap);
    }

    public Map getCfgMap() {

        return cfgMap;
    }

    public void setCfgMap(Map cfgMap) {

        this.cfgMap = cfgMap;
    }

}
