package com.github.btpka3.elasticsearch.plugin.analysis;

import org.elasticsearch.common.inject.Module;
import org.elasticsearch.index.analysis.AnalysisModule;
import org.elasticsearch.plugins.AbstractPlugin;

import java.util.ArrayList;
import java.util.Collection;

/**
 *
 */
public class PinyinAbbrPlugin extends AbstractPlugin {

    @Override
    public String name() {
        return "analysis-pinyin-abbr";
    }

    @Override
    public String description() {
        return "Chinese to Pinyin convert support with abbreviation";
    }

    @Override
    public Collection<Class<? extends Module>> modules() {
        Collection<Class<? extends Module>> classes = new ArrayList<Class<? extends Module>>();
        classes.add(PinyinAbbrIndicesAnalysisModule.class);
        return classes;
    }

    public void onModule(AnalysisModule module) {
        module.addProcessor(new PinyinAbbrAnalysisBinderProcessor());
    }
}
