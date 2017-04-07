package com.newsapp.ayman.newsapp;

import com.newsapp.ayman.newsapp.models.Channel;
import com.newsapp.ayman.newsapp.models.Rss;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Ayman on 4/6/2017.
 */

public interface Endpoint {

    @GET("/rss-feed-term/122/rss.xml")
    Call<Rss> getNews();

}
