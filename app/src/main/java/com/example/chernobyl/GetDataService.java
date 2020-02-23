package com.example.chernobyl;

import com.example.chernobyl.classes.RetroData;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("/photos")
    Call<List<RetroData>> getAllPhotos();
}