package com.newsapp.ayman.newsapp.models;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

/**
 * Created by Ayman on 4/6/2017.
 */
@Root(name = "rss",strict = false)
public class Rss {

    @Element(name = "channel")
    Channel channel;

    public Channel getChannel() {
        return channel;
    }
}
