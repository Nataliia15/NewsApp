package com.example.newsapp;

import android.content.Context;
import android.widget.Toast;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsLoader {
    Context context;
    List<ArticleModel>list=new ArrayList<>();
    RecyclerView recyclerView;
    NewsAdapter adapter;
    String category;

    public NewsLoader(Context context, RecyclerView recyclerView,String category) {
        this.context = context;
        this.list = list;
        this.recyclerView = recyclerView;
        this.category=category;
    }

    public  void loadNews(){

        Call<ResponseModel> call;
        final APIInterface apiInterface = ApiClient.getClient().create(APIInterface.class);

        call = apiInterface.getNews(category);
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                // List<ArticleModel>items;
                if (response.isSuccessful()) {
                    list = response.body().getArticles();
                    if(list.size()>0){
                        NewsAdapter adapter=new NewsAdapter(new MyRecyclerViewListener(context),list,context);
                        recyclerView.setAdapter(adapter);
                        recyclerView.setLayoutManager(new LinearLayoutManager(context)); }
                } else {
                    Toast.makeText(context, "response.body return null!", Toast.LENGTH_LONG).show();
                }
            }
            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(context, t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                //Log.d(TAG,t.getStackTrace().toString());
                t.printStackTrace();
            }
        });

    }
}
