package com.example.newstime.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.helper.widget.Layer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newstime.MainActivity;
import com.example.newstime.NewsDetailActivity;
import com.example.newstime.R;
import com.example.newstime.responseModel.Articles;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private ArrayList<Articles> articlesList;
    private Context context;

    public NewsAdapter(ArrayList<Articles> articlesList, Context context) {
        this.articlesList = articlesList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull  ViewGroup parent, int viewType) {
        //In this function we inflate the singleNewsItem view
        //and then return the viewHolder , The VieHolder constructor gets called
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_news_item,parent,false);
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull  NewsAdapter.ViewHolder holder, int position) {
                //By this stage we have already refer the views and now we can set the values of it
        //holder is the Viewholder which contains the referred Views
        //position refers to the index of the objects the adapter gets
        Articles articleSingle=articlesList.get(position);
        holder.newsHeading.setText(articleSingle.getTitle());
        holder.newsSubtitle.setText(articleSingle.getDescription());
        Picasso.get().load(articleSingle.getUrlToImage()).into(holder.newsImage);

        //setting onClickListner
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, NewsDetailActivity.class);
                intent.putExtra("title",articleSingle.getTitle());
                intent.putExtra("content",articleSingle.getContent());
                intent.putExtra("desc",articleSingle.getDescription());
                intent.putExtra("imageUrl",articleSingle.getUrlToImage());
                intent.putExtra("url",articleSingle.getUrl());

                //passing the intent to the Android SDK and then starting the activity
                context.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        //returns the size of list
        return articlesList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView newsHeading,newsSubtitle;
        private ImageView newsImage;


        public ViewHolder(@NonNull View itemView) {
            //itemView contains the inflated View
            super(itemView);
            //we have to refer the subViews from the Inflated Views
            newsHeading=(TextView)itemView.findViewById(R.id.newsTextHeading);
            newsSubtitle=(TextView)itemView.findViewById(R.id.newsTextSubtitle);
            newsImage=(ImageView) itemView.findViewById(R.id.newsImage);
        }
    }
}
