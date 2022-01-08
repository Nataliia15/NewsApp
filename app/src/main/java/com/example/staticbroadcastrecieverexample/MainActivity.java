package com.example.staticbroadcastrecieverexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener{
    public static final String NEWS_API_KEY="71997cdd2f454395b07465d8f853f5f5";
    RecyclerView recyclerView;
    NewsAdapter adapter;
    TextView textView;
    List<ArticleModel>items=new ArrayList<>();
    ArticleModel item;
    String text;
    private static final String TAG="MainActivity.MY_TAG";
    //ExampleBroadcactReciever reciever=new ExampleBroadcastReciever;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        textView = findViewById(R.id.text);
       /* Retrofit retrofit=new Retrofit.Builder()
                .baseUrl("https://newsapi.org/v2/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();*/
        final APIInterface apiInterface = ApiClient.getClient().create(APIInterface.class);
        Call<ResponseModel> call = apiInterface.getNews();

        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    items = response.body().getArticles();
                   // Toast.makeText(getApplicationContext(), String.valueOf(items.size()), Toast.LENGTH_SHORT).show();
                  /*  Iterator<ArticleModel> iterator=items.iterator();

                    while (iterator.hasNext()){

                        item=iterator.next();
                        text+=item.toString();}

                    textView.setText(text);*/
                    if(items.size()>0){
                    adapter = new NewsAdapter(MainActivity.this, new ArrayList<>(items), MainActivity.this);
                    Toast.makeText(getApplicationContext(), String.valueOf(items.size()), Toast.LENGTH_SHORT).show();
                    recyclerView.setAdapter(adapter);
                    recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter.notifyDataSetChanged();}
                } else {

                    Toast.makeText(getApplicationContext(), "response.body return null!", Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseModel> call, Throwable t) {
                Toast.makeText(getApplicationContext(), t.getMessage().toString(), Toast.LENGTH_SHORT).show();
                Log.d(TAG,t.getStackTrace().toString());
                t.printStackTrace();

            }
        });


    }


    @Override
    public void onItemClick(int position, View view) {
        ArticleModel articleModel=(ArticleModel)view.getTag();
        Intent intent=new Intent(this,WebActivity.class);
        intent.putExtra("url",articleModel.getUrl());
        startActivity(intent);

    }
}
