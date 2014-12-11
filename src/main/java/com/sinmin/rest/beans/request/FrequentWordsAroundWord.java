package com.sinmin.rest.beans.request;

/**
 * Created by dimuthuupeksha on 12/11/14.
 */
public class FrequentWordsAroundWord {

    private String value;
    private int range;
    private int[] time;
    private String[] category;
    private int amount;


    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
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

    public void setCategory(String[] category) {
        this.category = category;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
