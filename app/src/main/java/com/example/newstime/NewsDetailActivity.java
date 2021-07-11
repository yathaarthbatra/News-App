package com.example.newstime;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class NewsDetailActivity extends AppCompatActivity {

    private String title,des,content,imageUrl,url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        //recieving the intent data sent by the mainActivity
        title=getIntent().getStringExtra("title");
        des=getIntent().getStringExtra("desc");
        content=getIntent().getStringExtra("content");
        imageUrl=getIntent().getStringExtra("imageUrl");
        url=getIntent().getStringExtra("url");


    }
}