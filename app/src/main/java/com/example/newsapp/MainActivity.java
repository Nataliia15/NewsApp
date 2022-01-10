package com.example.newsapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecyclerViewItemClickListener , NavigationView.OnNavigationItemSelectedListener {

    RecyclerView recyclerView;
    NavigationView navigationView;
    DrawerLayout drawerLayout;
    NewsAdapter adapter;
    TextView textView;
    List<ArticleModel>items=new ArrayList<>();
    ArticleModel item;
    String text;
    private static final String TAG="MainActivity.MY_TAG";
    Toolbar toolbar;
    MenuItem checkedItem;
    private static final String CHECKED_ITEM_ID="checkedItem";
    Menu menu;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.recyclerView);
        drawerLayout=findViewById(R.id.drawerLayout);
        textView = findViewById(R.id.text);
        navigationView=findViewById(R.id.nav_view);
        toolbar=findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        menu=navigationView.getMenu();

        ActionBarDrawerToggle toggle=new ActionBarDrawerToggle(this,drawerLayout,toolbar,R.string.nav_open,R.string.nav_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if(savedInstanceState==null){
       loadNews(null);}
       navigationView.setNavigationItemSelectedListener(this);
      /* navigationView.setCheckedItem(R.id.business);
       onNavigationItemSelected(menu.findItem(R.id.business));*/



    }


    @Override
    public void onItemClick(int position, View view) {
        ArticleModel articleModel=(ArticleModel)view.getTag();
        Intent intent=new Intent(this,WebActivity.class);
        intent.putExtra("url",articleModel.getUrl());
        startActivity(intent);

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.general:
                loadNews("general");
                toolbar.setTitle("General");
                break;
            case R.id.business:
                loadNews("business");
                toolbar.setTitle("Business");
                Toast.makeText(this,"Restore",Toast.LENGTH_SHORT).show();
                break;
            case R.id.health:
                loadNews("health");
                toolbar.setTitle("Health");
                break;
            case R.id.science:
                loadNews("science");
                toolbar.setTitle("Science");
                break;
            case R.id.sport:
                loadNews("sports");
                toolbar.setTitle("Sport");
                break;
            case R.id.technology:
                loadNews("technology");
                toolbar.setTitle("Technology");
                break;
            case R.id.entertainment:
                loadNews("entertainment");
                toolbar.setTitle("Entertainment");
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
    public void loadNews(String category){
        Call<ResponseModel> call;
        final APIInterface apiInterface = ApiClient.getClient().create(APIInterface.class);
        if(category==null){
            call = apiInterface.getNews();
        }else{
         call = apiInterface.getNews(category);}
        call.enqueue(new Callback<ResponseModel>() {
            @Override
            public void onResponse(Call<ResponseModel> call, Response<ResponseModel> response) {
                if (response.isSuccessful()) {
                    items = response.body().getArticles();
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
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else{
        super.onBackPressed();}
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        checkedItem=navigationView.getCheckedItem();
        Toast.makeText(this,"saving",Toast.LENGTH_SHORT).show();

        if(checkedItem!=null){

        outState.putInt(CHECKED_ITEM_ID, checkedItem.getItemId());}

    }

    @Override
    protected void onRestoreInstanceState(@NonNull Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
            int id=savedInstanceState.getInt(CHECKED_ITEM_ID);
            // Toast.makeText(this,"Restore",Toast.LENGTH_SHORT).show();
            navigationView.setCheckedItem(id);
            //Menu menu=navigationView.getMenu();
            checkedItem=menu.findItem(id);



            onNavigationItemSelected(checkedItem);
        loadNews("health");
        toolbar.setTitle("Health");

    }
}
