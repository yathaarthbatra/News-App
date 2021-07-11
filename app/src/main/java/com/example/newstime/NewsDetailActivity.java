package com.example.newstime;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class NewsDetailActivity extends AppCompatActivity {

    private String title,des,content,imageUrl,url;
    private TextView titleView,contentView,desView;
    private ImageView imageView;
    private Button buttonView;

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

        //referring the views
        titleView=findViewById(R.id.newsDetailTitle);
        contentView=findViewById(R.id.newsDetailContent);
        desView=findViewById(R.id.newsDetailDesc);
        imageView=findViewById(R.id.newsDetailImage);
        buttonView=findViewById(R.id.newsDetailButton);

        //inserting the data into the views
        titleView.setText(title);
        contentView.setText(content);
        desView.setText(des);
        Picasso.get().load(imageUrl).into(imageView);
        buttonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //will define implicit intent
                Intent intent=new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);

            }
        });


    }
}