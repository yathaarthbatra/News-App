package com.example.newstime.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newstime.R;
import com.example.newstime.models.CategoryModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    private ArrayList<CategoryModel> categories;
    private Context context;
    private CategoryClickInterface categoryClickInterface;

    public CategoryAdapter(ArrayList<CategoryModel> categories, Context context, CategoryClickInterface categoryClickInterface) {
        this.categories = categories;
        this.context = context;
        this.categoryClickInterface = categoryClickInterface;
    }

    @Override
    public ViewHolder onCreateViewHolder( ViewGroup parent, int viewType) {

        //inflating the Views
        View view= LayoutInflater.from(parent.getContext())
                .inflate(R.layout.single_category_design,parent,false);

        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder( CategoryAdapter.ViewHolder holder, int position) {

        //setting the Values
        CategoryModel singleCategory=categories.get(position);
        holder.categoryText.setText(singleCategory.getCategory());
        Picasso.get().load(singleCategory.getCategoryImageUrl()).into(holder.categoryImage);

        //adding onClick Listner
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                categoryClickInterface.onCategoryClick(position);

            }
        });

    }

    @Override
    public int getItemCount() {
        return categories.size();
    }

    public interface CategoryClickInterface{
        void onCategoryClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView categoryImage;
        private TextView categoryText;

        public ViewHolder(@NonNull  View itemView) {
            super(itemView);
            //itemView contains inflated View
            //refering the views
            categoryImage=itemView.findViewById(R.id.categoryImage);
            categoryText=itemView.findViewById(R.id.categoryText);
        }
    }
}
