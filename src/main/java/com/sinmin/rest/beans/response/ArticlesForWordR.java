package com.sinmin.rest.beans.response;

/**
 * Created by dimuthuupeksha on 12/11/14.
 */
public class ArticlesForWordR {
    private int time;
    private String category;
    private ArticleR[] articles;

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public ArticleR[] getArticles() {
        return articles;
    }

    public void setArticles(ArticleR[] articles) {
        this.articles = articles;
    }
}
