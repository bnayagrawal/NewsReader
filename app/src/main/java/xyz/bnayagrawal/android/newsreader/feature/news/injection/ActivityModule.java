package xyz.bnayagrawal.android.newsreader.feature.news.injection;


import android.app.Activity;
import android.content.Context;

import javax.inject.Named;

import dagger.Module;
import dagger.Provides;

@Module
public class ActivityModule {

    private final Context context;

    ActivityModule(Activity context){
        this.context = context;
    }

    @Named("application_context")
    @MyApplicationScope
    @Provides
    public Context provideContext(){ return context; }
}