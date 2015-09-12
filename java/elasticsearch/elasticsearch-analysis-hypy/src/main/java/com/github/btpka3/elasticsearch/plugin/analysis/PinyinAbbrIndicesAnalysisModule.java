package com.github.btpka3.elasticsearch.plugin.analysis;

import org.elasticsearch.common.inject.AbstractModule;

/**
 *
 */
public class PinyinAbbrIndicesAnalysisModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(PinyinAbbrIndicesAnalysis.class).asEagerSingleton();
    }
}
