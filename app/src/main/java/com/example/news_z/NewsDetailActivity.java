package com.example.news_z;

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
    String title , des, content , url, imageUrl ;
    private TextView titleTv , desTv , contentTv;
    private ImageView imageView;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);
        title = getIntent().getStringExtra("title");
        des = getIntent().getStringExtra("des");
        content = getIntent().getStringExtra("content");
        url = getIntent().getStringExtra("url");
        imageUrl = getIntent().getStringExtra("image");

        titleTv = findViewById(R.id.detailTitle);
        desTv = findViewById(R.id.detailSubDes);
        contentTv = findViewById(R.id.detailContent);
        button = findViewById(R.id.readButton);
        imageView = findViewById(R.id.detailImage);

        titleTv.setText(title);
        desTv.setText(des);
        contentTv.setText(content);
        Picasso.get().load(imageUrl).into(imageView);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(url));
                startActivity(i);
            }
        });


    }
}