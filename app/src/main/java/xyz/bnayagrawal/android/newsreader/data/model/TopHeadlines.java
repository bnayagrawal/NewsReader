package xyz.bnayagrawal.android.newsreader.data.model;

import java.util.List;

public final class TopHeadlines {

    private String status;
    private int totalResults;
    private List<Article> articles;

    public TopHeadlines(String status, int totalResults, List<Article> articles) {
        this.status = status;
        this.totalResults = totalResults;
        this.articles = articles;
    }

    public String getStatus() {
        return status;
    }

    public int getTotalResults() {
        return totalResults;
    }

    public List<Article> getArticles() {
        return articles;
    }
}
