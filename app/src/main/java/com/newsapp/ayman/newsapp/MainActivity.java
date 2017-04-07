package com.newsapp.ayman.newsapp;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import android.support.v4.util.Pair;
import android.view.View;

import com.newsapp.ayman.newsapp.database.NewsDataSource;
import com.newsapp.ayman.newsapp.models.News;
import com.newsapp.ayman.newsapp.models.Rss;
import com.zplesac.connectionbuddy.ConnectionBuddy;
import com.zplesac.connectionbuddy.ConnectionBuddyConfiguration;
import com.zplesac.connectionbuddy.activities.ConnectionBuddyActivity;
import com.zplesac.connectionbuddy.models.ConnectivityEvent;
import com.zplesac.connectionbuddy.models.ConnectivityState;

import java.util.List;

import iammert.com.library.Status;
import iammert.com.library.StatusView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import tr.xip.errorview.ErrorView;

public class MainActivity extends ConnectionBuddyActivity implements NewsAdapterListener {

    public static final String NEWS_PARCELABLE_EXTRA = "news_extra_key";

    RecyclerView recyclerView;
    NewsRecyclerViewAdapter newsRecyclerViewAdapter;

    // to display internet connection status
    private StatusView statusView;

    // only displayed when there is no data AND there is no internet connection
    private ErrorView mErrorView;

    // to communicate with local database
    NewsDataSource dataSource;

    void initialViews(){
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));

        mErrorView = (ErrorView) findViewById(R.id.error_view);
        statusView = (StatusView) findViewById(R.id.status);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // initial and open the data source
        dataSource = new NewsDataSource(this);
        dataSource.open();

        // to monitor internet connection status
        ConnectionBuddyConfiguration networkInspectorConfiguration = new ConnectionBuddyConfiguration.Builder(this).build();
        ConnectionBuddy.getInstance().init(networkInspectorConfiguration);


        initialViews();


        getAndParseNews();
    }

    void getAndParseNews(){
        Endpoint endpoint = ServiceGenerator.createService(Endpoint.class);
        Call<Rss> responseCall = endpoint.getNews();
        responseCall.enqueue(new Callback<Rss>() {
            @Override
            public void onResponse(Call<Rss> call, Response<Rss> response) {
                List<News> newsList;

                if(response.isSuccessful()) {
                    newsList = response.body().getChannel().getNewsList();
                    insertNewsIntoDatabase(newsList);
                }
            }

            @Override
            public void onFailure(Call<Rss> call, Throwable t) {
                List<News> newsList = dataSource.getAllNews();

                // if there is prior stored data just display it
                if(newsList.size() != 0) {
                    setRecyclerViewAdapter(newsList);
                    mErrorView.setVisibility(View.INVISIBLE);
                } else {
                    // to inform user that there is no internet connection and he can click on retry to check the internet connection again
                    mErrorView.setVisibility(View.VISIBLE);
                    mErrorView.setOnRetryListener(new ErrorView.RetryListener() {
                        @Override
                        public void onRetry() {
                            statusView.setStatus(Status.LOADING);

                            Handler handler = new Handler();
                            handler.postDelayed(new Runnable() {
                                public void run() {
                                    statusView.setStatus(Status.ERROR);
                                }
                            }, 1000);

                        }
                    });
                }
            }
        });

    }

    /**
     * tp store the new News into database and displaying the data
     * @param newsList
     */
    void insertNewsIntoDatabase(List<News> newsList){
        for(News news : newsList) {
            dataSource.insertNewsRow(news);
        }

        setRecyclerViewAdapter(dataSource.getAllNews());
    }


    void setRecyclerViewAdapter(List<News> newsList){
        if(newsList != null && newsList.size() != 0) {
            newsRecyclerViewAdapter = new NewsRecyclerViewAdapter(newsList, getApplicationContext(),this);
            recyclerView.setAdapter(newsRecyclerViewAdapter);

        }
    }

    @Override
    public void onConnectionChange(ConnectivityEvent event) {
        if (event.getState().getValue() == ConnectivityState.CONNECTED){
            // device has active internet connection
            statusView.setStatus(Status.COMPLETE);
            mErrorView.setVisibility(View.INVISIBLE);

            getAndParseNews();

        } else if(event.getState().getValue() == ConnectivityState.DISCONNECTED){
            // there is no active internet connection on this device
            if(newsRecyclerViewAdapter == null) {
                statusView.setStatus(Status.ERROR);
                mErrorView.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dataSource.open();
    }

    @Override
    protected void onPause() {
        super.onPause();
        dataSource.close();
    }

    @Override
    public void setOnNewsClickListener(View newsImageView, View newsTitle, News news) {
        Intent intent = new Intent(getApplicationContext(),DetailsActivity.class);
        intent.putExtra(NEWS_PARCELABLE_EXTRA,news);


        // initial Shared elements between clicked item and Details Activity
        Pair<View,String> pairNewsImage = Pair.create(newsImageView,getString(R.string.transition_news_image));
        Pair<View,String> pairNewsTitle = Pair.create(newsTitle,getString(R.string.transition_news_title));

        // set Shared elements
        Context mContext = newsImageView.getContext();
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(this,pairNewsImage,pairNewsTitle);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            startActivity(intent,options.toBundle());
        } else {
            startActivity(intent);
        }
    }
}
