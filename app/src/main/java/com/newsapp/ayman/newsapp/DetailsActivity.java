package com.newsapp.ayman.newsapp;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.newsapp.ayman.newsapp.models.News;
import com.squareup.picasso.Picasso;

public class DetailsActivity extends AppCompatActivity {


    ImageView newsImageView;
    TextView newsTitle;
    TextView newsDetails;


    void initialViews(){
        newsImageView = (ImageView) findViewById(R.id.news_image);
        newsTitle = (TextView) findViewById(R.id.news_title);
        newsDetails = (TextView) findViewById(R.id.news_description);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        initialViews();

        News news = getIntent().getParcelableExtra(MainActivity.NEWS_PARCELABLE_EXTRA);

        newsTitle.setText(news.getTitle());
        newsDetails.setText(news.getDescription());

        Picasso.with(getApplicationContext()).load("http://www.egyptindependent.com/"+ news.getImage()).into(newsImageView);
    }

}
