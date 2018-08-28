package xyz.bnayagrawal.android.newsreader.feature.news.topheadlines;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Objects;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.bnayagrawal.android.newsreader.R;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsApiServer;
import xyz.bnayagrawal.android.newsreader.feature.news.NewsActivity;

public class TopHeadlinesFragment extends Fragment {

    @BindView(R.id.tab_layout)
    TabLayout tabLayout;
    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate layout and bind views
        View view = inflater.inflate(R.layout.fragment_top_headlines, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((NewsActivity) Objects.requireNonNull(getActivity())).setToolbar(R.id.toolbar_top_headlines);
        initTabs();
    }

    /**
     * Initializes Tabs
     */
    private void initTabs() {
        //For each available category, add tabs
        for (String category : NewsApiServer.POSSIBLE_CATEGORY_OPTIONS)
            tabLayout.addTab(tabLayout.newTab().setText(category));

        PagerAdapter pagerAdapter = new TopHeadlinesTabPagerAdapter(
                getChildFragmentManager(),
                tabLayout.getTabCount()
        );

        viewPager.setAdapter(pagerAdapter);

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                /////////////////////
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {
                /////////////////////
            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_activity_news, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
