package com.sinmin.rest.beans.request;

/**
 * Created by dimuthuupeksha on 12/10/14.
 */
public class FrequentWord {
    private int[] time;
    private String[] category;
    private int amount;

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
