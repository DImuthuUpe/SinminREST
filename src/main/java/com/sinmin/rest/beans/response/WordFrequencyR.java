package com.sinmin.rest.beans.response;

import java.util.Map;

/**
 * Created by dimuthuupeksha on 12/10/14.
 */
public class WordFrequencyR {

    /* format
        {
            date = "2014",
            category = "news",
            frequency =12

        }
     */
    private int date;
    private String category;
    private int frequency;

    public int getDate() {
        return date;
    }

    public void setDate(int date) {
        this.date = date;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getFrequency() {
        return frequency;
    }

    public void setFrequency(int frequency) {
        this.frequency = frequency;
    }
}
