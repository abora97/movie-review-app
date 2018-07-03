package com.example.abora.senior.adapter;


import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.abora.senior.R;
import com.example.abora.senior.controller.activity.DetailActivity;
import com.example.abora.senior.model.Movie;
import com.example.abora.senior.util.Constant;
import com.mindorks.nybus.NYBus;
import com.mindorks.nybus.event.Channel;
import com.squareup.picasso.Picasso;

import java.util.List;


public class MoviesAdapter extends RecyclerView.Adapter<MoviesAdapter.MovieHolder> {

    Context context;
    List<Movie> list;

    public MoviesAdapter(Context context, List<Movie> list) {
        this.context = context;
        this.list = list;
    }


    @Override
    public MovieHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View row = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_row, parent, false);
        MovieHolder holder = new MovieHolder(row);
        return holder;
    }

    @Override
    public void onBindViewHolder(MovieHolder holder, int position) {
        final Movie movie = list.get(position);

        Picasso.get()
                .load(Constant.BASE_IMAGE_URL + movie.getPosterPath())
                .error(R.drawable.img)
                .placeholder(R.drawable.ic_launcher_background)
                .into(holder.iv);
        holder.tv.setText(movie.getTitle());

        holder.ly.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!context.getResources().getBoolean(R.bool.isTablet)){
                    Intent intent = new Intent(context, DetailActivity.class);
                    intent.putExtra(Constant.MOVIE_EXTRA, movie);
                    context.startActivity(intent);
                }else {
                    NYBus.get().post(movie,Channel.ONE);
                    //    NYBus.get().post(movie);
                }

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    class MovieHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv;
        LinearLayout ly;

        public MovieHolder(View itemView) {
            super(itemView);
            iv = (ImageView) itemView.findViewById(R.id.ivrow);
            tv = (TextView) itemView.findViewById(R.id.txrow);
            ly = (LinearLayout) itemView.findViewById(R.id.root);
        }
    }
}

