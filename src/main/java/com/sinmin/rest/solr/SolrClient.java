package com.sinmin.rest.solr;

import com.sinmin.rest.beans.response.WildCardR;
import corpus.sinhala.wildcard.search.SolrWildCardSearch;

import java.util.List;

/**
 * Created by dimuthuupeksha on 1/8/15.
 */
public class SolrClient {
    public WildCardR[] getWildCardWords(String val){
        SolrWildCardSearch solrWildCardSearch = new SolrWildCardSearch();
        List<String> wordList= solrWildCardSearch.searchWord(val, true);
        if(wordList!=null){
            WildCardR [] wordArr = new WildCardR[wordList.size()];
            for(int i=0;i<wordList.size();i++){
                WildCardR resp = new WildCardR();
                resp.setValue(wordList.get(i));
                wordArr[i]=resp;
            }
            return wordArr;
        }
        return null;
    }
}
