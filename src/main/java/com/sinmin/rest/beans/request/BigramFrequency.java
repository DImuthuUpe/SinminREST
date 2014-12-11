package com.sinmin.rest.beans.request;

/**
 * Created by dimuthuupeksha on 12/10/14.
 */
public class BigramFrequency {
    private String value1;
    private String value2;
    private int[] time;
    private String category[];

    public String getValue1() {
        return value1;
    }

    public void setValue1(String value1) {
        this.value1 = value1;
    }

    public String getValue2() {
        return value2;
    }

    public void setValue2(String value2) {
        this.value2 = value2;
    }

    public int[] getTime() {
        return time;
    }

    public void setTime(int[] time) {
        this.time = time;
    }

    public String[] getCategory() {
        return category;
    }

    public void setCategory(String category[]) {
        this.category = category;
    }
}
