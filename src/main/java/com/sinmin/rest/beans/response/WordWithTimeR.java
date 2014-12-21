package com.sinmin.rest.beans.response;

/**
 * Created by dimuthuupeksha on 12/22/14.
 */
public class WordWithTimeR {
    private String word;
    private int year;
    private int frequency;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
