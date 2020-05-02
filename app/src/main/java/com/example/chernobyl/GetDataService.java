package com.example.chernobyl;

import com.example.chernobyl.classes.MainCategoryData;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface GetDataService {

    @GET("api/maincategory/?format=json")     //Endpoint
    Call<List<MainCategoryData>> requestData();
}