package xyz.bnayagrawal.android.newsreader.data.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.inject.Singleton;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import xyz.bnayagrawal.android.newsreader.AppExecutors;
import xyz.bnayagrawal.android.newsreader.data.local.NewsDao;
import xyz.bnayagrawal.android.newsreader.data.model.Article;
import xyz.bnayagrawal.android.newsreader.data.model.Everything;
import xyz.bnayagrawal.android.newsreader.data.model.Sources;
import xyz.bnayagrawal.android.newsreader.data.model.TopHeadlines;
import xyz.bnayagrawal.android.newsreader.resource.NetworkBoundResource;
import xyz.bnayagrawal.android.newsreader.resource.Resource;

import static xyz.bnayagrawal.android.newsreader.data.remote.NewsApiServer.STATUS_OK;

@Singleton
public class NewsRepository {

    private final NewsService newsService;
    private final NewsDao newsDao;
    private final AppExecutors appExecutors;

    @Inject
    public NewsRepository(NewsService newsService, NewsDao newsDao, AppExecutors appExecutors) {
        this.newsService = newsService;
        this.newsDao = newsDao;
        this.appExecutors = appExecutors;
    }

    public LiveData<Resource<List<Article>>> getEverything(String apiKey, Map<String, String> options) {
        return new NetworkBoundResource<List<Article>, Everything>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull Everything everything) {
                //set news type and category
                for(Article article: everything.getArticles()) {
                    article.setType(NewsApiServer.NewsType.EVERYTHING.name());
                    try{article.setSourceName(article.getSource().getName());}catch (Exception ignored){}
                }

                newsDao.clearCachedArticles(NewsApiServer.NewsType.EVERYTHING.name());
                newsDao.cacheArticles(everything.getArticles());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return newsDao.getCachedArticles(NewsApiServer.NewsType.EVERYTHING.name());
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<Everything>> createCall() {
                return newsService.getEverything(apiKey,options);
            }
        }.getAsLiveData();
    }

    public LiveData<Resource<List<Article>>> getTopHeadlines(String apiKey, String category, Map<String, String> options) {
        return new NetworkBoundResource<List<Article>, TopHeadlines>(appExecutors) {

            @Override
            protected void saveCallResult(@NonNull TopHeadlines topHeadlines) {
                //set news type and category
                for(Article article: topHeadlines.getArticles()) {
                    article.setType(NewsApiServer.NewsType.TOP_HEADLINES.name());
                    article.setCategory(category);
                    try{article.setSourceName(article.getSource().getName());}catch (Exception ignored){}
                }

                newsDao.clearCachedArticles(NewsApiServer.NewsType.TOP_HEADLINES.name(),category);
                newsDao.cacheArticles(topHeadlines.getArticles());
            }

            @Override
            protected boolean shouldFetch(@Nullable List<Article> data) {
                return true;
            }

            @NonNull
            @Override
            protected LiveData<List<Article>> loadFromDb() {
                return newsDao.getCachedArticles(NewsApiServer.NewsType.TOP_HEADLINES.name(),category);
            }

            @NonNull
            @Override
            protected LiveData<ApiResponse<TopHeadlines>> createCall() {
                return newsService.getTopHeadlines(apiKey,options);
            }
        }.getAsLiveData();
    }

    public LiveData<List<Sources.SourceDetails>> getSources(String apiKey, Map<String, String> options) {
        final MutableLiveData<List<Sources.SourceDetails>> data = new MutableLiveData<>();
        newsService.getSources(apiKey, options).enqueue(new Callback<Sources>() {
            @Override
            public void onResponse(@NonNull Call<Sources> call, @NonNull Response<Sources> response) {
                if (response.body().getStatus().equals(STATUS_OK))
                    data.setValue(response.body().getSources());
                else
                    data.setValue(null);
            }

            @Override
            public void onFailure(@NonNull Call<Sources> call, @NonNull Throwable t) {
            }
        });
        return data;
    }
}
