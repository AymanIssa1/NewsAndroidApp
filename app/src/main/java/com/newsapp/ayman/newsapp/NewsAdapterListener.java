package com.newsapp.ayman.newsapp;

import android.view.View;

import com.newsapp.ayman.newsapp.models.News;

/**
 * Created by Ayman on 4/7/2017.
 */

public interface NewsAdapterListener {

    void setOnNewsClickListener(View newsImageView,View newsTitle, News news);

}
