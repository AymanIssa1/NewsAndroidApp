package com.newsapp.ayman.newsapp.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.newsapp.ayman.newsapp.models.News;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman on 4/7/2017.
 */

public class NewsDataSource {

    //Database fields
    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_TITLE,
            MySQLiteHelper.COLUMN_DESCRIPTION,
            MySQLiteHelper.COLUMN_IMAGE};

    public NewsDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close(){
        dbHelper.close();
    }

    public void insertNewsRow(News news) {
        ContentValues values = new ContentValues();

        values.put(MySQLiteHelper.COLUMN_ID,news.getId());
        values.put(MySQLiteHelper.COLUMN_TITLE,news.getTitle());
        values.put(MySQLiteHelper.COLUMN_DESCRIPTION,news.getDescription());
        values.put(MySQLiteHelper.COLUMN_IMAGE,news.getImage());

        try {
            database.insert(MySQLiteHelper.TABLE_NEWS,null,values);
        } catch (Exception e) {
            Log.e("insertNewsRow Exception",e.getMessage());
        }


    }

    public List<News> getAllNews(){
        List<News> newsList = new ArrayList<>();

        Cursor cursor = database.query(MySQLiteHelper.TABLE_NEWS,allColumns, null, null, null, null, null);
        cursor.moveToFirst();

        while (!cursor.isAfterLast()) {
            News news = cursorToNews(cursor);
            newsList.add(news);
            cursor.moveToNext();
        }

        cursor.close();
        return newsList;
    }

    public News cursorToNews(Cursor cursor) {
        News news = new News();
        news.setId(cursor.getInt(0));
        news.setTitle(cursor.getString(1));
        news.setDescription(cursor.getString(2));
        news.setImage(cursor.getString(3));
        return news;
    }

}
