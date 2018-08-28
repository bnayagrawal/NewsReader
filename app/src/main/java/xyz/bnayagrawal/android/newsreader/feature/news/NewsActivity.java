package xyz.bnayagrawal.android.newsreader.feature.news;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import butterknife.BindView;
import butterknife.ButterKnife;
import xyz.bnayagrawal.android.newsreader.R;
import xyz.bnayagrawal.android.newsreader.feature.news.everything.EverythingFragment;
import xyz.bnayagrawal.android.newsreader.feature.news.topheadlines.TopHeadlinesFragment;

public class NewsActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener,
        FragmentManager.OnBackStackChangedListener {

    private static final String FRAGMENT_TOP_HEADLINES = "fragment_top_headlines";
    private static final String FRAGMENT_EVERYTHING = "fragment_everything";

    @BindView(R.id.drawer_layout)
    DrawerLayout drawer;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.AppTheme);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news);
        ButterKnife.bind(this);
        setupView();
        if (savedInstanceState == null)
            setInitialFragment();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            if (count == 1)
                Toast.makeText(
                        this,
                        "Press back button once again to exit!",
                        Toast.LENGTH_SHORT)
                        .show();
            if(count == 0)
                super.onBackPressed();
            else
                getSupportFragmentManager().popBackStack();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_activity_news, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        switch (item.getItemId()) {
            case R.id.nav_top_headlines:
                if (!isFragmentVisible(FRAGMENT_TOP_HEADLINES))
                    setFragment(FRAGMENT_TOP_HEADLINES);
                break;
            case R.id.nav_everything:
                if (!isFragmentVisible(FRAGMENT_EVERYTHING))
                    setFragment(FRAGMENT_EVERYTHING);
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onBackStackChanged() {
        if (isFragmentVisible(FRAGMENT_TOP_HEADLINES))
            navigationView.setCheckedItem(R.id.nav_top_headlines);
        else if (isFragmentVisible(FRAGMENT_EVERYTHING))
            navigationView.setCheckedItem(R.id.nav_everything);
    }

    private void setupView() {
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_top_headlines);
        getSupportFragmentManager().addOnBackStackChangedListener(this);
    }

    private void setInitialFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .add(
                        R.id.fragment_container,
                        new TopHeadlinesFragment(),
                        FRAGMENT_TOP_HEADLINES
                )
                .commit();
    }

    /**
     * To be called by the fragments
     *
     * @param toolbar_id id of the toolbar
     */
    public void setToolbar(int toolbar_id) {
        Toolbar toolbar = findViewById(toolbar_id);
        ActionBarDrawerToggle toggle;
        if (toolbar != null) {
            setSupportActionBar(toolbar);
            toggle = new ActionBarDrawerToggle(
                    this,
                    drawer,
                    toolbar,
                    R.string.navigation_drawer_open,
                    R.string.navigation_drawer_close) {

                public void onDrawerClosed(View view) {
                    super.onDrawerClosed(view);
                    // To perform an action only after the drawer is closed
                }

                public void onDrawerOpened(View drawerView) {
                    super.onDrawerOpened(drawerView);
                    // No use so far
                }
            };
            drawer.addDrawerListener(toggle);
            toggle.syncState();
        }
    }

    private boolean isFragmentVisible(String fragmentTag) {
        boolean result = false;
        switch (fragmentTag) {
            case FRAGMENT_TOP_HEADLINES: {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_TOP_HEADLINES);
                if (fragment instanceof TopHeadlinesFragment)
                    if (fragment.isVisible())
                        result = true;
                break;
            }
            case FRAGMENT_EVERYTHING: {
                Fragment fragment = getSupportFragmentManager().findFragmentByTag(FRAGMENT_EVERYTHING);
                if (fragment instanceof EverythingFragment)
                    if (fragment.isVisible())
                        result = true;
                break;
            }
        }
        return result;
    }

    private void clearFragmentBackStack() {
        int count = getSupportFragmentManager().getBackStackEntryCount();
        for (int i = 0; i < count; ++i)
            getSupportFragmentManager().popBackStackImmediate();
    }

    private void setFragment(String fragmentTag) {
        Fragment fragment = null;
        switch (fragmentTag) {
            case FRAGMENT_TOP_HEADLINES:
                clearFragmentBackStack();
                fragment = new TopHeadlinesFragment();
                break;
            case FRAGMENT_EVERYTHING:
                fragment = new EverythingFragment();
                break;
        }

        if (null == fragment) return;
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment, fragmentTag)
                .addToBackStack(fragmentTag)
                .commit();
    }
}
