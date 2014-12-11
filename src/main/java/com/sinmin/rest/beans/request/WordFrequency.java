package com.sinmin.rest.beans.request;

/**
 * Created by dimuthuupeksha on 12/10/14.
 */
public class WordFrequency {
    private String value;
    private int[] time;
    private String[] category;

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int[] getTime() {
        return time;
    }

    public void setTime(int[] time) {
        this.time = time;
    }
}
