package com.example.galleryapp.remoteDataSource;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Photo {

    @SerializedName("photo")
    @Expose
    private ImageDetails[] photo;

    public Photo(ImageDetails[] photo) {
        this.photo = photo;
    }

    public ImageDetails[] getPhoto() {
        return photo;
    }
}
