package com.example.chris.test5;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.chris.test5.model.Article;
import com.example.chris.test5.util.DatabaseHelper;

public class NewsDetailsActivity extends AppCompatActivity
{
    
    private static final String TAG = NewsDetailsActivity.class.getSimpleName() + "_TAG";
    private TextView tvSource;
    private TextView tvAuthor;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvUrl;
    private ImageView ivPic;
    private TextView tvPublished;
    private DatabaseHelper helper;
    private boolean saved;
    private Article article;
    private long row;
    
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details);
    
        bindViews();
        
        helper = new DatabaseHelper(this);
    
        checkArticleInDatabase();
    }
    
    private void bindViews()
    {
        tvSource = findViewById(R.id.tvSource);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvTitle = findViewById(R.id.tvNTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvUrl = findViewById(R.id.tvUrl);
        ivPic = findViewById(R.id.ivPic);
        tvPublished = findViewById(R.id.tvNPublished);
    }
    
    private void checkArticleInDatabase()
    {
        article = (Article) getIntent().getSerializableExtra("news");
        if (article != null)
        {
            Article dbArticle = helper.getArticle(article.getPublishedAt());
            Log.d(TAG, "onCreate: "+dbArticle);
            if (dbArticle != null)
            {
                saved = true;
            }
            else
            {
                saved = false;
            }
    
    
            displayArticleInformation();
            
        }
    }
    
    private void displayArticleInformation()
    {
        tvSource.setText("Source:\n"+ article.getSource().getName());
        tvAuthor.setText("Author:\n"+ article.getAuthor());
        tvTitle.setText("Title:\n"+ article.getTitle());
        tvDescription.setText("Description:\n"+ article.getDescription());
        tvUrl.setText("Url:\n"+ article.getUrl());
        Glide.with(this).load(article.getUrlToImage()).into(ivPic);
        tvPublished.setText("Published:\n"+ article.getPublishedAt());
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        if (saved)
            getMenuInflater().inflate(R.menu.main_menu, menu);
        else
            getMenuInflater().inflate(R.menu.main_menu2, menu);
        return true;
    }
    
    public void onSaveNews(View view)
    {
        if (!saved)
        {
            row = helper.saveArticle(article);
            if (row > 0)
            {
                Toast.makeText(this, "Article Saved!", Toast.LENGTH_SHORT).show();
                saved = true;
                Intent intent = new Intent(this, NewsDetailsActivity.class);
                intent.putExtra("news", article);
                startActivity(intent);
                finish();
            }
            else
            {
                Toast.makeText(this, "Failed to Save", Toast.LENGTH_SHORT).show();
            }
        }
        else
            Toast.makeText(this, "Article has already been saved!", Toast.LENGTH_SHORT).show();
    }
}
