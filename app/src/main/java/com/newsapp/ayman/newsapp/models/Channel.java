package com.newsapp.ayman.newsapp.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ayman on 4/6/2017.
 */

@Root(name = "channel",strict = false)
public class Channel {

    @Element(name = "title")
    String title;

    @Element(name = "link")
    String link;

    @Element(name = "language")
    String language;

    @ElementList(name = "item",inline = true, required = false)
    List<News> newsList;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsArrayList(ArrayList<News> newsArrayList) {
        this.newsList = newsArrayList;
    }
}
