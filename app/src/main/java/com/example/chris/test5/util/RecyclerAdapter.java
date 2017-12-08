package com.example.chris.test5.util;

import android.content.Context;
import android.content.Intent;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.chris.test5.NewsDetailsActivity;
import com.example.chris.test5.R;
import com.example.chris.test5.model.Article;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by Admin on 11/27/2017.
 */

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>
{
    List<Article> articles = new ArrayList<>();
    Context context;

    public RecyclerAdapter(List<Article> articles)
    {
        this.articles = articles;
//        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.recycle_list_item, null);
        context = parent.getContext();
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position)
    {
        Article article = articles.get(position);
        if(article != null)
        {
            holder.tvTitle.setText(article.getTitle());
            holder.tvPublished.setText(article.getPublishedAt());
        }
    }

    @Override
    public int getItemCount()
    {
        return articles.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        private final TextView tvTitle;
        private final TextView tvPublished;
        public ViewHolder(final View itemView)
        {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            tvPublished = itemView.findViewById(R.id.tvPublished);
            
            itemView.setOnClickListener(new View.OnClickListener()
            {
                @Override
                public void onClick(View v)
                {
                    Intent intent = new Intent(context, NewsDetailsActivity.class);
                    intent.putExtra("news", articles.get(getAdapterPosition()));
                    context.startActivity(intent);
                }
            });
        }
    }
}
