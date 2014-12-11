package com.sinmin.rest.beans.response;

/**
 * Created by dimuthuupeksha on 12/11/14.
 */
public class WordPositionR {
    private int time;
    private String category;
    private WordR[] words;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public WordR[] getWords() {
        return words;
    }

    public void setWords(WordR[] words) {
        this.words = words;
    }
}
