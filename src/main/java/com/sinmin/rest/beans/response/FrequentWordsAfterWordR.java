package com.sinmin.rest.beans.response;

import java.util.Map;

/**
 * Created by dimuthuupeksha on 12/21/14.
 */
public class FrequentWordsAfterWordR {
    private WordWithTimeR[] words;
    private String category;

    public WordWithTimeR[] getWords() {
        return words;
    }

    public void setWords(WordWithTimeR[] words) {
        this.words = words;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
