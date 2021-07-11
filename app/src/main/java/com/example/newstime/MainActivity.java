package com.example.newstime;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.newstime.adapters.CategoryAdapter;
import com.example.newstime.adapters.NewsAdapter;
import com.example.newstime.models.CategoryModel;
import com.example.newstime.responseModel.Articles;
import com.example.newstime.responseModel.NewsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements CategoryAdapter.CategoryClickInterface{

    private RecyclerView newsRecycler,categoryRecyler;
    private ProgressBar progressBar;
    //as we have to pass the data to the adapter
    private ArrayList<Articles> articles;
    private ArrayList<CategoryModel> categories;
    private CategoryAdapter categoryAdapter;
    private NewsAdapter newsAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        newsRecycler=findViewById(R.id.newsRecyclerView);
        categoryRecyler=findViewById(R.id.categoriesRecyclerView);
        progressBar=findViewById(R.id.progressBar);
        articles=new ArrayList<>();
        categories=new ArrayList<>();
        newsAdapter=new NewsAdapter(articles,this);
        categoryAdapter=new CategoryAdapter(categories,this,this::onCategoryClick);

        newsRecycler.setLayoutManager(new LinearLayoutManager(this));
        newsRecycler.setAdapter(newsAdapter);
        categoryRecyler.setAdapter(categoryAdapter);
        getCategories(); //initally our categories list was empty but after calling this
        //function the categories data set will get changed
        getNews("All");
        newsAdapter.notifyDataSetChanged();

    }

    private void getCategories(){
        categories.add(new CategoryModel("All","https://images.unsplash.com/photo-1495020689067-958852a7765e?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8bmV3c3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categories.add(new CategoryModel("Technology","https://images.unsplash.com/photo-1488590528505-98d2b5aba04b?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"));
        categories.add(new CategoryModel("Science","https://images.unsplash.com/photo-1582560475093-ba66accbc424?ixid=MnwxMjA3fDB8MHxzZWFyY2h8MjJ8fHNjaWVuY2V8ZW58MHx8MHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categories.add(new CategoryModel("Sports","https://images.unsplash.com/photo-1461896836934-ffe607ba8211?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"));
        categories.add(new CategoryModel("General","https://images.unsplash.com/photo-1585829365295-ab7cd400c167?ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8bmV3c3xlbnwwfHwwfHw%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categories.add(new CategoryModel("Health","https://images.unsplash.com/photo-1505751172876-fa1923c5c528?ixid=MnwxMjA3fDB8MHxzZWFyY2h8Nnx8aGVhbHRofGVufDB8fDB8fA%3D%3D&ixlib=rb-1.2.1&auto=format&fit=crop&w=500&q=60"));
        categories.add(new CategoryModel("Business","https://images.unsplash.com/39/lIZrwvbeRuuzqOoWJUEn_Photoaday_CSD%20(1%20of%201)-5.jpg?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"));
        categories.add(new CategoryModel("Entertainment","https://images.unsplash.com/photo-1603190287605-e6ade32fa852?ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&ixlib=rb-1.2.1&auto=format&fit=crop&w=1350&q=80"));

        //inform the adapter that the data has been changed
        categoryAdapter.notifyDataSetChanged();

    }

    private void getNews(String category){
        progressBar.setVisibility(View.VISIBLE);
        articles.clear();//clearing the old category news


        String categoryUrl="https://newsapi.org/v2/top-headlines?country=in&apiKey=6b3e2bc189fd4b3eaa5d8831525ad188&category="+ category;
        String urlAllNews="https://newsapi.org/v2/top-headlines?apiKey=6b3e2bc189fd4b3eaa5d8831525ad188&country=in&excludeDomains=stackoverflow.com&language=en&sortBy=publishedAt";
        String baseUrl="https://newsapi.org/";

        //Creating Retrofit object
        Retrofit retrofit=new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiInterface retrofitApi=retrofit.create(ApiInterface.class);

        Call<NewsModel> call;
        if(category.equals("All")){
            call=retrofitApi.getAllNews(urlAllNews);
        }
        else{
            call= retrofitApi.getNewsByCategory(categoryUrl);
        }
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                //if the response if successful
                NewsModel responseNewsModel=response.body();
                progressBar.setVisibility(View.GONE); //as soon as we get the response
                ArrayList<Articles> tempArticles=responseNewsModel.getArticles();
                for(int i=0;i<tempArticles.size();i++){
                    articles.add(
                            new Articles(tempArticles.get(i).getTitle(),
                                    tempArticles.get(i).getDescription(),
                                    tempArticles.get(i).getUrlToImage(),
                                    tempArticles.get(i).getUrl(),
                                    tempArticles.get(i).getContent()));

                }
                newsAdapter.notifyDataSetChanged();

            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Failed to get News",Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public void onCategoryClick(int position) {
        String category=categories.get(position).getCategory();
        getNews(category);

    }
}