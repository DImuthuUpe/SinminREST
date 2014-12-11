package com.sinmin.rest.beans.request;

/**
 * Created by dimuthuupeksha on 12/11/14.
 */
public class WordPosition {
    private int position;
    private int[] time;
    private String[] category;
    private int amount;

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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
