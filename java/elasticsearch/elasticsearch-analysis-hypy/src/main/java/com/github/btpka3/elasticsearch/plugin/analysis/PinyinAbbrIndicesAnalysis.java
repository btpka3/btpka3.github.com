package com.github.btpka3.elasticsearch.plugin.analysis;

import org.elasticsearch.common.component.AbstractComponent;
import org.elasticsearch.common.settings.Settings;

/**
 *
 */
public class PinyinAbbrIndicesAnalysis extends AbstractComponent {
    public PinyinAbbrIndicesAnalysis(Settings settings) {
        super(settings);
    }

    public PinyinAbbrIndicesAnalysis(Settings settings, String prefixSettings) {
        super(settings, prefixSettings);
    }

    public PinyinAbbrIndicesAnalysis(Settings settings, Class customClass) {
        super(settings, customClass);
    }

    public PinyinAbbrIndicesAnalysis(Settings settings, String prefixSettings, Class customClass) {
        super(settings, prefixSettings, customClass);
    }

    public PinyinAbbrIndicesAnalysis(Settings settings, Class loggerClass, Class componentClass) {
        super(settings, loggerClass, componentClass);
    }

    public PinyinAbbrIndicesAnalysis(Settings settings, String prefixSettings, Class loggerClass, Class componentClass) {
        super(settings, prefixSettings, loggerClass, componentClass);
    }
}
