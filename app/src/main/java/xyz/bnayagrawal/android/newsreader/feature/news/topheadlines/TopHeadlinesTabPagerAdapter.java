package xyz.bnayagrawal.android.newsreader.feature.news.topheadlines;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import xyz.bnayagrawal.android.newsreader.data.remote.NewsApiServer;

import static xyz.bnayagrawal.android.newsreader.feature.news.topheadlines.TopHeadlinesTabFragment.EXTRA_NEWS_CATEGORY;

public class TopHeadlinesTabPagerAdapter extends FragmentPagerAdapter {

    private int tabCount;

    public TopHeadlinesTabPagerAdapter(FragmentManager fragmentManager, int tabCount) {
        super(fragmentManager);
        this.tabCount = tabCount;
    }

    @Override
    public Fragment getItem(int position) {
        TopHeadlinesTabFragment fragment = new TopHeadlinesTabFragment();

        //Set news category
        Bundle bundle = new Bundle();
        bundle.putString(EXTRA_NEWS_CATEGORY,NewsApiServer.POSSIBLE_CATEGORY_OPTIONS[position]);
        fragment.setArguments(bundle);

        return fragment;
    }

    @Override
    public int getCount() {
        return tabCount;
    }
}
