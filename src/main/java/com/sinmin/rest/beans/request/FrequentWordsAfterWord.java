package com.sinmin.rest.beans.request;

/**
 * Created by dimuthuupeksha on 12/21/14.
 */
public class FrequentWordsAfterWord {
    private String value;
    private int[] time;
    private String[] category;
    private int amount;

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
