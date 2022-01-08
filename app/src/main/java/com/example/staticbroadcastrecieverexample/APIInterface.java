package com.example.staticbroadcastrecieverexample;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIInterface {

    @GET("v2/top-headlines?category=health&apiKey=71997cdd2f454395b07465d8f853f5f5")
    Call<ResponseModel> getNews();
}
