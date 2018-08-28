package xyz.bnayagrawal.android.newsreader.feature.news;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.customtabs.CustomTabsIntent;
import android.view.View;

import xyz.bnayagrawal.android.newsreader.R;
import xyz.bnayagrawal.android.newsreader.data.model.Article;

public class ArticlePresenter {

    private static final int ACTION_SHARE_REQUEST_CODE = 0;
    /**
     * Triggered when an article is clicked
     *
     * @param article article
     */
    public void onItemClick(View view, Article article) {
        String articleUrl = article.getUrl();
        if(null == articleUrl || articleUrl.length() == 0)
            return;

        Context context = view.getContext();
        CustomTabsIntent.Builder builder = new CustomTabsIntent.Builder();
        //extra settings
        builder.setToolbarColor(context.getResources().getColor(R.color.colorAccent));
        builder.setStartAnimations(context, R.anim.slide_in_left, R.anim.slide_out_left);
        builder.setExitAnimations(context, R.anim.slide_in, R.anim.slide_out);
        builder.addMenuItem("Share link via...",getShareButtonAction(context,articleUrl));

        /* NOT WORKING!
        builder.setActionButton(
                BitmapFactory.decodeResource(context.getResources(),R.drawable.ic_share),
                "Share",getShareButtonAction(context,articleUrl),true
        );*/

        CustomTabsIntent customTabsIntent = builder.build();
        customTabsIntent.launchUrl(context, Uri.parse(articleUrl));
    }

    private PendingIntent getShareButtonAction(Context context, String articleUrl) {
        Intent shareLinkIntent = new Intent(Intent.ACTION_SEND);
        shareLinkIntent.putExtra(Intent.EXTRA_TEXT,articleUrl);
        shareLinkIntent.setType("text/plain");
        return PendingIntent.getActivity(
                context,
                ACTION_SHARE_REQUEST_CODE,
                Intent.createChooser(
                        shareLinkIntent,
                        "Share link"
                ),
                PendingIntent.FLAG_UPDATE_CURRENT
        );
    }
}
