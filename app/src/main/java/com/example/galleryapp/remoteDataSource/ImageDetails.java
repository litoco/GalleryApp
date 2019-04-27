package com.example.galleryapp.remoteDataSource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageDetails {

    @SerializedName("id")
    @Expose
    private String id;

    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("url_s")
    @Expose
    private String url_s;

    public ImageDetails(String id, String title, String url) {
        this.id = id;
        this.title = title;
        this.url_s = url;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getUrl() {
        return url_s;
    }
}
