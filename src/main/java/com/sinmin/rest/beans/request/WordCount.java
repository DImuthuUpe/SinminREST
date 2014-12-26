package com.sinmin.rest.beans.request;

/**
 * Created by dimuthuupeksha on 12/25/14.
 */
public class WordCount {
    private int[] time;
    private String[] category;

    public int[] getTime() {
        return time;
    }

    public void setTime(int[] time) {
        this.time = time;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String[] category) {
        this.category = category;
    }
}
