package xyz.bnayagrawal.android.newsreader.feature.news.injection;

import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ContextModule {
    public Context context;

    public ContextModule(Context context){
        this.context = context;
    }

    @Named("activity_context")
    @MyApplicationScope
    @Provides
    public Context provideContext(){ return context.getApplicationContext(); }
}
