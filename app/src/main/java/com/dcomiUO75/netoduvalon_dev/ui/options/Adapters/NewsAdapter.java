package com.dcomiUO75.netoduvalon_dev.ui.options.Adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.prof.rssparser.Article;
import com.squareup.picasso.Picasso;
import com.dcomiUO75.netoduvalon_dev.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private List<Article> articles;

    private Context mContext;


    public NewsAdapter(List<Article> list, Context context) {
        this.articles = list;
        this.mContext = context;
    }

    public List<Article> getArticleList() {
        return articles;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_news, viewGroup, false);
        return new ViewHolder(v);
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    public void onBindViewHolder(@NonNull final ViewHolder viewHolder, int position) {

        Article currentArticle = articles.get(position);

        String pubDateString = currentArticle.getPubDate();

        try {
            String sourceDateString = currentArticle.getPubDate();

            if (sourceDateString != null) {
                SimpleDateFormat sourceSdf = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z", Locale.ENGLISH);
                Date date = sourceSdf.parse(sourceDateString);
                if (date != null) {
                    SimpleDateFormat sdf = new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                    pubDateString = sdf.format(date);
                }
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }

        viewHolder.title.setText(currentArticle.getTitle());

        // Parseo de la etiqueta descripcion
        viewHolder.descripcion.setText(currentArticle.getDescription().substring(currentArticle.getDescription().indexOf("<em>") + 4, currentArticle.getDescription().indexOf("</em>")));


        Picasso.get()
                .load(currentArticle.getImage())
                .placeholder(R.drawable.placeholder)
                .into(viewHolder.image);

        viewHolder.pubDate.setText(pubDateString);

        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            // Ir a la pagina de cada articulo
            public void onClick(View view) {
                String url = articles.get(viewHolder.getAbsoluteAdapterPosition()).getLink();
                Uri uri = Uri.parse(url);
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                mContext.startActivity(intent);
            }

        });
    }

    @Override
    public int getItemCount() {
        return articles == null ? 0 : articles.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView pubDate;
        ImageView image;
        TextView descripcion;

        public ViewHolder(View itemView) {

            super(itemView);
            title = itemView.findViewById(R.id.title);
            pubDate = itemView.findViewById(R.id.pubDate);
            image = itemView.findViewById(R.id.image);
            descripcion = itemView.findViewById(R.id.description);

        }
    }

}
