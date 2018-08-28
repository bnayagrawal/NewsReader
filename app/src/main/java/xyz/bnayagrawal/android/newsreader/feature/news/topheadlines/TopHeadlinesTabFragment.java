package xyz.bnayagrawal.android.newsreader.feature.news.topheadlines;

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
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import jp.wasabeef.recyclerview.adapters.AlphaInAnimationAdapter;
import xyz.bnayagrawal.android.newsreader.BuildConfig;
import xyz.bnayagrawal.android.newsreader.R;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsApiServer;
import xyz.bnayagrawal.android.newsreader.data.remote.NewsApiServer.NewsType;
import xyz.bnayagrawal.android.newsreader.feature.news.NewsViewModel;
import xyz.bnayagrawal.android.newsreader.feature.news.NewsViewModelFactory;
import xyz.bnayagrawal.android.newsreader.feature.news.adapter.ArticleRecyclerAdapter;
import xyz.bnayagrawal.android.newsreader.feature.news.injection.ContextModule;
import xyz.bnayagrawal.android.newsreader.feature.news.injection.DaggerNewsServiceComponent;
import xyz.bnayagrawal.android.newsreader.feature.news.injection.NewsServiceModule;

import static android.widget.GridLayout.VERTICAL;

public class TopHeadlinesTabFragment extends Fragment implements LifecycleOwner {

    public static final String EXTRA_NEWS_CATEGORY
            = "xyz.bnayagrawal.android.newsreader.feature.news.category";

    @BindView(R.id.swipe_refresh_layout)
    SwipeRefreshLayout swipeRefreshLayout;
    @BindView(R.id.recycler_news)
    RecyclerView recyclerNews;

    private Map<String, String> options = new HashMap<>();
    private ArticleRecyclerAdapter adapter;

    @Inject
    NewsViewModelFactory factory;

    private NewsViewModel viewModel;
    private String category = null;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        //Get news category
        Bundle args = getArguments();
        if (null != args)
            category = args.getString(EXTRA_NEWS_CATEGORY, null);

        //This should not happen but...
        if (null == category) {
            Toast.makeText(getContext(), "Something went wrong :(", Toast.LENGTH_LONG).show();
            getActivity().finish();
        }

        //Inflate layout and bind views
        View view = inflater.inflate(R.layout.tab_news, container, false);
        ButterKnife.bind(this, view);

        //Injection
        DaggerNewsServiceComponent
                .builder()
                .contextModule(new ContextModule(getContext()))
                .newsServiceModule(new NewsServiceModule())
                .build()
                .inject(this);

        //setup views
        setupView();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        viewModel = ViewModelProviders.of(this, factory).get(NewsViewModel.class);
        refresh();
    }

    /**
     * Fetches news from network/disk
     */
    private void refresh() {
        options.put(NewsApiServer.ALL_QUERY_PARAM_CATEGORY,category);
        options.put(NewsApiServer.ALL_QUERY_PARAM_COUNTRY, "in");
        options.put(NewsApiServer.EVERYTHING_QUERY_PARAM_LANGUAGE, "en");

        viewModel.fetchTopHeadlines(BuildConfig.NEWS_API_KEY, category, options);
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
