package com.sinmin.rest.beans.response;

import java.util.HashMap;

/**
 * Created by dimuthuupeksha on 12/10/14.
 */
public class FrequentWordR {
    private WordR[] value1;
    private WordR[] value2;
    private WordR[] value3;
    private String category;
    private int time;

    public WordR[] getValue1() {
        return value1;
    }

    public void setValue1(WordR[] value1) {
        this.value1 = value1;
    }

    public WordR[] getValue2() {
        return value2;
    }

    public void setValue2(WordR[] value2) {
        this.value2 = value2;
    }

    public WordR[] getValue3() {
        return value3;
    }

    public void setValue3(WordR[] value3) {
        this.value3 = value3;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
