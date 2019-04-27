package com.example.galleryapp.remoteDataSource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photos {


    @SerializedName("photos")
    @Expose
    private Photo photos;

    public Photos(Photo photos) {
        this.photos = photos;
    }

    public Photo getPhotos() {
        return photos;
    }
}
