package xyz.bnayagrawal.android.newsreader.feature.news.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.bumptech.glide.GenericTransitionOptions;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Collection;

import xyz.bnayagrawal.android.newsreader.R;
import xyz.bnayagrawal.android.newsreader.data.model.Article;
import xyz.bnayagrawal.android.newsreader.databinding.ItemArticleBinding;
import xyz.bnayagrawal.android.newsreader.feature.news.ArticlePresenter;

public class ArticleRecyclerAdapter extends RecyclerView.Adapter<ArticleRecyclerAdapter.ViewHolder> {

    private Collection<Article> articles;
    private Context context;

    public ArticleRecyclerAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        ItemArticleBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_article, parent, false);
        binding.setPresenter(new ArticlePresenter());
        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Article article = (Article) articles.toArray()[position];
        holder.binding.setArticle(article);

        //Set article image
        Glide.with(context).load(article.getUrlToImage())
                .apply(new RequestOptions().centerCrop().placeholder(R.drawable.ic_placeholder_thumb))
                .transition(GenericTransitionOptions.with(R.anim.fade_in))
                .into(holder.binding.imgArticleImage);
    }

    @Override
    public int getItemCount() {
        if (articles != null)
            return articles.size();
        else
            return 0;
    }

    public void setArticles(Collection<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private final ItemArticleBinding binding;

        ViewHolder(final ItemArticleBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
    }
}
