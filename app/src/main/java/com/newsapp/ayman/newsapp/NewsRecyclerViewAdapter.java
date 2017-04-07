package com.newsapp.ayman.newsapp;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.newsapp.ayman.newsapp.models.News;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Ayman on 4/6/2017.
 */

public class NewsRecyclerViewAdapter extends RecyclerView.Adapter<NewsRecyclerViewAdapter.NewsRecyclerViewHolder> {

    private List<News> newsList;
    private Context context;

    private NewsAdapterListener newsAdapterListener;

    public NewsRecyclerViewAdapter(List<News> newsList, Context context, NewsAdapterListener newsAdapterListener) {
        this.newsList = newsList;
        this.context = context;
        this.newsAdapterListener = newsAdapterListener;
    }

    @Override
    public NewsRecyclerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_large_item,null);
        return new NewsRecyclerViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(final NewsRecyclerViewHolder holder, int position) {
        final News news = newsList.get(position);

        holder.setNewsTitle(news.getTitle());
        holder.setNewsImageView(news.getImage(),context);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                newsAdapterListener.setOnNewsClickListener(holder.newsImageView,holder.newsTitleTextView,news);
            }
        });
    }

    @Override
    public int getItemCount() {
        return newsList.size();
    }

    public class NewsRecyclerViewHolder extends RecyclerView.ViewHolder {

        private ImageView newsImageView;
        private TextView newsTitleTextView;

        public NewsRecyclerViewHolder(View itemView) {
            super(itemView);
            newsImageView = (ImageView) itemView.findViewById(R.id.news_image);
            newsTitleTextView = (TextView) itemView.findViewById(R.id.news_title);
        }

        public void setNewsImageView(String url,Context context) {
            Picasso.with(context).load("http://www.egyptindependent.com/"+url).into(newsImageView);

        }

        public void setNewsTitle(String newsTitle) {
            newsTitleTextView.setText(newsTitle);
        }

    }

}
