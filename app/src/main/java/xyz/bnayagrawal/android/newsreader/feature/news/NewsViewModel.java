package xyz.bnayagrawal.android.newsreader.feature.news;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import java.util.List;
import java.util.Map;

import xyz.bnayagrawal.android.newsreader.data.model.Article;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsRepository;
import xyz.bnayagrawal.android.newsreader.resource.Resource;

public class NewsViewModel extends ViewModel {

    private NewsRepository newsRepository;

    private LiveData<Resource<List<Article>>> articles;

    NewsViewModel(NewsRepository newsRepository) {
        this.newsRepository = newsRepository;
    }

    public void fetchTopHeadlines(String apiKey, String category, Map<String, String> options) {
        articles = newsRepository.getTopHeadlines(apiKey, category, options);
    }

    public void fetchEverything(String apiKey, Map<String, String> options) {
        articles = newsRepository.getEverything(apiKey, options);
    }

    public LiveData<Resource<List<Article>>> getArticles() {
        return this.articles;
    }
}
