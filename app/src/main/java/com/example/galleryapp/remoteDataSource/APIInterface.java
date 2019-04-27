package com.example.galleryapp.remoteDataSource;


import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface APIInterface {

    @GET
    Call<Photos> getPhotoDetails(@Url String url);
}
