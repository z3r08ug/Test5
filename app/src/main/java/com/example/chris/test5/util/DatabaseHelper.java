package com.example.chris.test5.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.chris.test5.model.Article;
import com.example.chris.test5.model.Source;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Admin on 11/14/2017.
 */

public class DatabaseHelper extends SQLiteOpenHelper
{
    
    public DatabaseHelper(Context context)
    {
        super(context, DatabaseContract.Entry.TABLE_NAME, null, 1);
    }
    
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        String CREATE_TABLE = "CREATE TABLE " + DatabaseContract.Entry.TABLE_NAME + "(" + DatabaseContract.Entry.COLUMN_PUB
                + " TEXT PRIMARY KEY," +
                DatabaseContract.Entry.COLUMN_SOURCE + " TEXT, " + DatabaseContract.Entry.COLUMN_AUTHOR+" TEXT ,"+
                DatabaseContract.Entry.COLUMN_DESC + " TEXT ," + DatabaseContract.Entry.COLUMN_URL + " TEXT ," +
                DatabaseContract.Entry.COLUMN_PIC + " TEXT ," + DatabaseContract.Entry.COLUMN_TITLE + " TEXT " +
                ")";
        db.execSQL(CREATE_TABLE);
    }
    
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.Entry.TABLE_NAME);
        onCreate(db);
    }
    
    public long saveArticle(Article article)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        
        ContentValues contentValues = new ContentValues();
        contentValues.put(DatabaseContract.Entry.COLUMN_PUB, article.getPublishedAt());
        contentValues.put(DatabaseContract.Entry.COLUMN_SOURCE, article.getSource().getName());
        contentValues.put(DatabaseContract.Entry.COLUMN_AUTHOR, article.getAuthor());
        contentValues.put(DatabaseContract.Entry.COLUMN_DESC, article.getDescription());
        contentValues.put(DatabaseContract.Entry.COLUMN_URL, article.getUrl());
        contentValues.put(DatabaseContract.Entry.COLUMN_PIC, article.getUrlToImage());
        contentValues.put(DatabaseContract.Entry.COLUMN_TITLE, article.getTitle());
        long row = db.insert(DatabaseContract.Entry.TABLE_NAME, null,contentValues);
        return row;
    }
    
    public List<Article> getArticles()
    {
        List<Article> articles = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        
        String QUERY = "SELECT * FROM " + DatabaseContract.Entry.TABLE_NAME;
        Cursor cursor = db.rawQuery(QUERY, null);
        
        if(cursor.moveToFirst())
        {
            do
            {
                Article article = new Article(
                        new Source(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(6),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(0)
                );
                articles.add(article);
            }
            while (cursor.moveToNext());
        }
        return articles;
    }
    
    public Article getArticle(String published)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        Article article = null;
        
        String QUERY = "SELECT * FROM " + DatabaseContract.Entry.TABLE_NAME + " WHERE Published='"+published+"'";
        Cursor cursor = db.rawQuery(QUERY, null);
        
        if(cursor.moveToFirst())
        {
            do
            {
                article = new Article(
                        new Source(cursor.getString(1)),
                        cursor.getString(2),
                        cursor.getString(6),
                        cursor.getString(3),
                        cursor.getString(4),
                        cursor.getString(5),
                        cursor.getString(0)
                );
            }
            while (cursor.moveToNext());
        }
        return article;
    }
}
