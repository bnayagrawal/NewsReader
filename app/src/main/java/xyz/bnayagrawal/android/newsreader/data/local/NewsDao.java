package xyz.bnayagrawal.android.newsreader.data.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import xyz.bnayagrawal.android.newsreader.data.model.Article;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface NewsDao {

    @Insert(onConflict = REPLACE)
    void cacheArticles(List<Article> articles);

    @Query("SELECT * FROM articles WHERE type = :type AND category = :category")
    LiveData<List<Article>> getCachedArticles(String type, String category);

    @Query("SELECT * FROM articles WHERE type = :type")
    LiveData<List<Article>> getCachedArticles(String type);

    @Query("DELETE FROM articles WHERE type = :type AND category = :category")
    void clearCachedArticles(String type, String category);

    @Query("DELETE FROM articles WHERE type = :type")
    void clearCachedArticles(String type);

    @Query("DELETE FROM articles")
    void clearAllCachedArticles();
}
