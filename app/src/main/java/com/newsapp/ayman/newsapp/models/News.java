package com.newsapp.ayman.newsapp.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.Html;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;


/**
 * Created by Ayman on 4/6/2017.
 */

@Root(name = "item",strict = false)
public class News implements Parcelable{

    @Element(name = "title")
    private String title;

    @Element(name = "link")
    private String link;

    @Element(name = "description")
    private String description;

    @Element(name = "pubDate")
    private String pubDate;

    @Element(name = "image")
    private String image;

    @Element(name = "guid")
    private String guid;

    private int id;

    public News() {
    }

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

    public String getDescription() {
        //remove first letter ( it was an image ) , trim and delete and large spaces in between
        String htmlString = String.valueOf(Html.fromHtml(description)).substring(1).trim().replaceAll("\n","");
        String htmlString2 = htmlString.replace("." , ".\n\n");

        return htmlString2;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public Integer getId(){
        // get the first few numbers before "at" as an id for primaryKey
        return Integer.valueOf(guid.substring(0,guid.indexOf("at")-1));
    }

    public void setId(int id) {
        this.id = id;
    }

    protected News(Parcel in) {
        title = in.readString();
        link = in.readString();
        description = in.readString();
        pubDate = in.readString();
        image = in.readString();
        guid = in.readString();
        id = in.readInt();
    }

    public static final Creator<News> CREATOR = new Creator<News>() {
        @Override
        public News createFromParcel(Parcel in) {
            return new News(in);
        }

        @Override
        public News[] newArray(int size) {
            return new News[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(link);
        dest.writeString(description);
        dest.writeString(pubDate);
        dest.writeString(image);
        dest.writeString(guid);
        dest.writeInt(id);
    }
}
