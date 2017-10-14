package com.example.user.news;

/**
 * Created by user on 10/10/2017.
 */

public class newsitem {
    private String newsHeading;
    private String newsDesc;
    private String newsDescSmall;
    private String time;

    private String url;
    private String imageURL;

    public newsitem(String newsHeading, String newsDesc,  String time, String url, String imageURL) {

        this.imageURL = imageURL;
        this.newsDesc = newsDesc;
        this.newsHeading = newsHeading;
        this.time = time;
        this.url = url;


    }


    public String getImageURL() {
        return imageURL;
    }

    public String getNewsDesc() {
        return newsDesc;
    }

    public String getNewsDescSmall() {
        return newsDescSmall;
    }

    public String getNewsHeading() {
        return newsHeading;
    }

    public String getTime() {
        return time;
    }

    public String getUrl() {
        return url;
    }
}
