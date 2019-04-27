package com.example.galleryapp;

public class SingleGridItem {

    private String mImageUrl;
    private String mImageName;
    private String mImageId;


    public SingleGridItem(String mImageUrl, String mImageName, String mImageId) {
        this.mImageUrl = mImageUrl;
        this.mImageName = mImageName;
        this.mImageId = mImageId;
    }

    public String getmImageId() {
        return mImageId;
    }

    public String getmImageUrl() {
        return mImageUrl;
    }

    public String getmImageName() {
        return mImageName;
    }
}
