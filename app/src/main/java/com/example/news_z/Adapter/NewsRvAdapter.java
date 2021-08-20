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

import com.example.news_z.Model.Articles;
import com.example.news_z.NewsDetailActivity;
import com.example.news_z.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class NewsRvAdapter extends RecyclerView.Adapter<NewsRvAdapter.ViewHolder> {
    private ArrayList<Articles>articlesArrayList;
    private Context context;

    public NewsRvAdapter(ArrayList<Articles> articlesArrayList, Context context) {
        this.articlesArrayList = articlesArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_rv_item , parent , false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Articles articles = articlesArrayList.get(position);
        holder.subtitleTv.setText(articles.getDescription());
        holder.titleTv.setText(articles.getTitle());
        Picasso.get().load(articles.getUrlToImage())
                .fit()
                .into(holder.newsIV);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context , NewsDetailActivity.class);
                intent.putExtra("title" , articles.getTitle());
                intent.putExtra("url" , articles.getUrl());
                intent.putExtra("image" , articles.getUrlToImage());
                intent.putExtra("des" , articles.getDescription());
                intent.putExtra("content" , articles.getContent());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return articlesArrayList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView titleTv , subtitleTv;
        private ImageView newsIV;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            titleTv = itemView.findViewById(R.id.idTvNewsHeadlines);
            subtitleTv = itemView.findViewById(R.id.idTiNewsSubHeadings);
            newsIV = itemView.findViewById(R.id.idViNews);
        }
    }
}
