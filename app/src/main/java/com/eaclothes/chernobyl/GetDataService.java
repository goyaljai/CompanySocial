package com.eaclothes.chernobyl;

import com.eaclothes.chernobyl.classes.AppData;
import com.eaclothes.chernobyl.classes.MainCategoryData;
import com.eaclothes.chernobyl.classes.MainSubCategoryData;

import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface GetDataService {

    @GET("maincategory/?format=json")     //Endpoint
    Call<List<MainCategoryData>> requestMainData();

    @GET("mainsubcategory/")     //Endpoint
    Call<List<MainSubCategoryData>> requestMainSubData(
            @Query("format") String format,
            @Query("maincategory_id") int id
    );

    @GET("app/")     //Endpoint
    Call<List<AppData>> requestAppData();
}