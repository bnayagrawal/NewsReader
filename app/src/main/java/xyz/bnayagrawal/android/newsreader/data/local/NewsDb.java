package xyz.bnayagrawal.android.newsreader.data.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.RoomDatabase;

import xyz.bnayagrawal.android.newsreader.data.model.Article;

@Database(entities = {Article.class}, version = 1, exportSchema = false)
public abstract class NewsDb extends RoomDatabase {
    public abstract NewsDao newsDao();
}
