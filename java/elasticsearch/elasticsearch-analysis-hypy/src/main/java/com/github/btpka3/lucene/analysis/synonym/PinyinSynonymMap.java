package com.github.btpka3.lucene.analysis.synonym;

import org.apache.lucene.analysis.synonym.SynonymMap;
import org.apache.lucene.util.BytesRef;
import org.apache.lucene.util.BytesRefHash;
import org.apache.lucene.util.fst.FST;

public class PinyinSynonymMap extends SynonymMap {

    public PinyinSynonymMap() {
        // zhuang
        super(null, null, 7);
    }

    private FST buildFST() {
        return null;
    }
}
