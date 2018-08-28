package xyz.bnayagrawal.android.newsreader.feature.news.injection;

import dagger.Component;
import xyz.bnayagrawal.android.newsreader.data.model.Everything;
import xyz.bnayagrawal.android.newsreader.feature.news.everything.EverythingFragment;
import xyz.bnayagrawal.android.newsreader.feature.news.topheadlines.TopHeadlinesTabFragment;

@MyApplicationScope
@Component(modules = {NewsServiceModule.class})
public interface NewsServiceComponent {

    void inject(TopHeadlinesTabFragment fragment);
    void inject(EverythingFragment fragment);
}
