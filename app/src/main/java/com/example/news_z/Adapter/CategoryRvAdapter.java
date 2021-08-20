package com.example.news_z.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.news_z.Model.CategoryRvModel;
import com.example.news_z.NewsDetailActivity;
import com.example.news_z.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class CategoryRvAdapter extends RecyclerView.Adapter<CategoryRvAdapter.ViewHolder> {
    private Context context;
    private ArrayList<CategoryRvModel> categoryRvModels;
    CategoryClickedInterface categoryClickedInterface;

    public CategoryRvAdapter(Context context, ArrayList<CategoryRvModel> categoryRvModels, CategoryClickedInterface categoryClickedInterface) {
        this.context = context;
        this.categoryRvModels = categoryRvModels;
        this.categoryClickedInterface = categoryClickedInterface;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.categories_rv_item , parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        CategoryRvModel categoryRvModel = categoryRvModels.get(position);
        holder.categoryTv.setText(categoryRvModel.getCategory());
        Picasso.get().load(categoryRvModel.getCategoryImageUrl()).fit().into(holder.categoryIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               categoryClickedInterface.OnCategoryClicked(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryRvModels.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView categoryTv;
        private ImageView categoryIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            categoryTv = itemView.findViewById(R.id.IdTVCategories);
            categoryIV = itemView.findViewById(R.id.idIVCategory);
        }
    }
    public interface CategoryClickedInterface{
        void OnCategoryClicked(int position);
    }


}
