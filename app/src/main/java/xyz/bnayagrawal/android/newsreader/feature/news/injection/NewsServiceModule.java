package xyz.bnayagrawal.android.newsreader.feature.news.injection;

import android.arch.persistence.room.Room;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import xyz.bnayagrawal.android.newsreader.AppExecutors;
import xyz.bnayagrawal.android.newsreader.data.local.NewsDao;
import xyz.bnayagrawal.android.newsreader.data.local.NewsDb;
import xyz.bnayagrawal.android.newsreader.data.remote.LiveDataCallAdapterFactory;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsApiServer;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsRepository;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsService;

@Module(includes = ContextModule.class)
public class NewsServiceModule {

    @MyApplicationScope
    @Named("newsService")
    @Provides
    NewsService provideNewsService(@Named("baseUrl") String baseUrl) {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(NewsService.class);
    }

    @Provides
    @Named("baseUrl")
    String provideBaseUrl() {
        return NewsApiServer.BASE_URL + NewsApiServer.BASE_ENDPOINT;
    }

    @MyApplicationScope
    @Provides
    NewsRepository provideNewsRepository(
            @Named("newsService") NewsService newsService,@Named("activity_context") Context context) {
        return new NewsRepository(newsService, provideNewsDao(provideNewsDb(context)), AppExecutors.getInstance());
    }

    @Provides
    NewsDb provideNewsDb(Context context) {
        return Room.databaseBuilder(context, NewsDb.class, "news.db")
                .build();
    }

    @Provides
    NewsDao provideNewsDao(NewsDb newsDb) {
        return newsDb.newsDao();
    }
}
