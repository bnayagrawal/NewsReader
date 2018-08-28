package xyz.bnayagrawal.android.newsreader.feature.news.everything;

import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import xyz.bnayagrawal.android.newsreader.BuildConfig;
import xyz.bnayagrawal.android.newsreader.R;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsApiServer;
import xyz.bnayagrawal.android.newsreader.feature.news.NewsActivity;
import xyz.bnayagrawal.android.newsreader.feature.news.NewsViewModel;
import xyz.bnayagrawal.android.newsreader.feature.news.NewsViewModelFactory;
import xyz.bnayagrawal.android.newsreader.feature.news.adapter.ArticleRecyclerAdapter;
import xyz.bnayagrawal.android.newsreader.feature.news.injection.ContextModule;
import xyz.bnayagrawal.android.newsreader.feature.news.injection.DaggerNewsServiceComponent;
import xyz.bnayagrawal.android.newsreader.feature.news.injection.NewsServiceModule;

import static android.widget.GridLayout.VERTICAL;

public class EverythingFragment extends Fragment implements LifecycleOwner {

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_news)
    RecyclerView recyclerNews;

    private Map<String, String> options = new HashMap<>();
    private ArticleRecyclerAdapter adapter;

    @Inject
    NewsViewModelFactory factory;

    private NewsViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        //Inflate layout and bind views
        View view = inflater.inflate(R.layout.fragment_everything, container, false);
        ButterKnife.bind(this, view);

        //Injection
        DaggerNewsServiceComponent
                .builder()
                .contextModule(new ContextModule(getContext()))
                .newsServiceModule(new NewsServiceModule())
                .build()
                .inject(this);

        setupView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        ((NewsActivity) Objects.requireNonNull(getActivity())).setToolbar(R.id.toolbar_everything);
        viewModel = ViewModelProviders.of(this, factory).get(NewsViewModel.class);
        refresh();
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

    /**
     * Fetches news from network/disk
     */
    private void refresh() {
        options.put(NewsApiServer.EVERYTHING_QUERY_PARAM_LANGUAGE, "en");
        options.put(NewsApiServer.QUERY_PARAM_QUERY,"Entertainment");
        viewModel.fetchEverything(BuildConfig.NEWS_API_KEY, options);

        viewModel.getArticles().observe(this, articles -> {
            if (null != articles) {
                switch (articles.status) {
                    case LOADING:
                        swipeRefreshLayout.setRefreshing(true);
                        break;
                    case SUCCESS:
                        swipeRefreshLayout.setRefreshing(false);
                        //update recycler view
                        adapter.setArticles(articles.data);
                        break;
                    case ERROR:
                        swipeRefreshLayout.setRefreshing(false);
                        //Notify user
                        break;
                }
            }
        });
    }

    /**
     * Sets adapter and listeners on views
     */
    private void setupView() {
        //setup recycler view
        recyclerNews.setLayoutManager(new LinearLayoutManager(getContext()));
        adapter = new ArticleRecyclerAdapter(getContext());
        recyclerNews.setAdapter(new AlphaInAnimationAdapter(adapter));
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), VERTICAL);
        recyclerNews.addItemDecoration(decoration);

        //Setup swipe refresh
        swipeRefreshLayout.setColorSchemeColors(getResources().getColor(R.color.colorAccent));
        swipeRefreshLayout.setOnRefreshListener(this::refresh);
    }
}
