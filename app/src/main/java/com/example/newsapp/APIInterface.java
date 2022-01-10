package com.example.newsapp;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("v2/top-headlines?apiKey="+MyConstants.NEWS_API_KEY)
    Call<ResponseModel> getNews(@Query("category")String category);

    @GET("v2/top-headlines?country=us&apiKey="+MyConstants.NEWS_API_KEY)
    Call<ResponseModel> getNews();
}
