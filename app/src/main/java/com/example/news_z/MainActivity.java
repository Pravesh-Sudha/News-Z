package com.example.news_z;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_z.Adapter.CategoryRvAdapter;
import com.example.news_z.Adapter.NewsRvAdapter;
import com.example.news_z.Model.Articles;
import com.example.news_z.Model.CategoryRvModel;
import com.example.news_z.Model.NewsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryRvAdapter.CategoryClickedInterface {
    String generalUrl = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apiKey=f30172b4cac44406aa3d6912fcbabe7a";
    RecyclerView newsRV , categoryRV;
    ProgressBar loadingPB;
    NewsRvAdapter newsRvAdapter;
    CategoryRvAdapter categoryRvAdapter;
    private ArrayList<Articles> articlesArrayList;
    private ArrayList<CategoryRvModel> categoryRvModelArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newsRV = findViewById(R.id.idRVNews);
        categoryRV = findViewById(R.id.idRVCategories);
        loadingPB = findViewById(R.id.idPBLoading);
        articlesArrayList = new ArrayList<>();
        categoryRvModelArrayList = new ArrayList<>();
        newsRvAdapter = new NewsRvAdapter(articlesArrayList , this);
        categoryRvAdapter = new CategoryRvAdapter(this , categoryRvModelArrayList , this::OnCategoryClicked);
        newsRV.setLayoutManager(new LinearLayoutManager(this));
        newsRV.setAdapter(newsRvAdapter);
        categoryRV.setAdapter(categoryRvAdapter);
        getCategories();
        getNews("All");
        newsRvAdapter.notifyDataSetChanged();

    }
    private void getCategories(){
        categoryRvModelArrayList.add(new CategoryRvModel("All" , "https://images.unsplash.com/photo-1590580343530-ea3b7e3dd243?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=447&q=80"));
        categoryRvModelArrayList.add(new CategoryRvModel("Technology" , "https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1050&q=80"));
        categoryRvModelArrayList.add(new CategoryRvModel("Health" , "https://images.unsplash.com/photo-1477332552946-cfb384aeaf1c?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        categoryRvModelArrayList.add(new CategoryRvModel("Science" , "https://images.unsplash.com/photo-1507668077129-56e32842fceb?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=334&q=80"));
        categoryRvModelArrayList.add(new CategoryRvModel("Sports" , "https://images.unsplash.com/photo-1541252260730-0412e8e2108e?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=322&q=80"));
        categoryRvModelArrayList.add(new CategoryRvModel("General" , "https://images.unsplash.com/photo-1489533119213-66a5cd877091?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=751&q=80"));
        categoryRvModelArrayList.add(new CategoryRvModel("Business" , "https://images.unsplash.com/photo-1578574577315-3fbeb0cecdc2?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=752&q=80"));
        categoryRvModelArrayList.add(new CategoryRvModel("Entertainment" , "https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=750&q=80"));
        categoryRvAdapter.notifyDataSetChanged();
    }

    private void getNews(String category){
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();

        String categoryUrl = "https://newsapi.org/v2/top-headlines?country=in&category="+ category +"&apiKey=f30172b4cac44406aa3d6912fcbabe7a";
        String url = generalUrl;
        String baseUrl = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI = retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if (category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else{
            call = retrofitAPI.getNewsByCategory(categoryUrl);
        }
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel = response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles = newsModel.getArticles();
                for (int i = 0; i < articles.size() ; i++) {
                    articlesArrayList.add(new Articles(articles.get(i).getTitle() ,
                                            articles.get(i).getDescription() , articles.get(i).getUrl()
                                            ,articles.get(i).getUrlToImage() ,
                                            articles.get(i).getContent()));
                }
                newsRvAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Failed to get News", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    public void OnCategoryClicked(int position) {
        String category = categoryRvModelArrayList.get(position).getCategory();
        getNews(category);
    }
}