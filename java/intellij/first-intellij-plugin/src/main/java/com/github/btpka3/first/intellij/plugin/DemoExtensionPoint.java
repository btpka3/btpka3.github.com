package com.github.btpka3.first.intellij.plugin;

import com.intellij.util.xmlb.annotations.Attribute;

/**
 * @author dangqian.zll
 * @date 2021/2/19
 */
public class DemoExtensionPoint {

    @Attribute("key")
    public String key;

    public String getKey() {
        return key;
    }

}
