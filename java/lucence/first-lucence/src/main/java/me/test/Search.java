package me.test;

import java.io.File;
import java.io.IOException;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.queryparser.classic.ParseException;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class Search {

    public static void main(String[] args) throws IOException, ParseException {

        // 模拟用户的搜索关键词
        String line = "a c";
        String field = "name";
        Analyzer analyzer = new StandardAnalyzer(Version.LUCENE_47);
        QueryParser parser = new QueryParser(Version.LUCENE_47, field, analyzer);
        Query query = parser.parse(line);


        int hitsPerPage = 3;
        IndexReader reader = DirectoryReader.open(FSDirectory.open(new File("myIdx")));
        IndexSearcher searcher = new IndexSearcher(reader);
        TopDocs results = searcher.search(query, 5 * hitsPerPage);

        ScoreDoc[] hits = results.scoreDocs;
        int numTotalHits = results.totalHits;

        for (int i = 0; i < hits.length; i++) {
            Document doc = searcher.doc(hits[i].doc);
            System.out.println(i+". doc=" + hits[i].doc + " score=" + hits[i].score + " doc=" + doc);
        }

        System.out.println("-------------------total : " + numTotalHits);

    }

}
