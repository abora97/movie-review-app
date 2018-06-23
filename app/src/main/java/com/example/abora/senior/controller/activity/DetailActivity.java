package com.example.abora.senior.controller.activity;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abora.senior.R;
import com.example.abora.senior.model.Movie;
import com.example.abora.senior.util.Constant;
import com.example.abora.senior.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    @BindView(R.id.cover_image)
    ImageView coverImage;
    @BindView(R.id.img)
    ImageView img;
    @BindView(R.id.titleTv)
    TextView titleTv;
    @BindView(R.id.ratingTv)
    TextView ratingTv;
    @BindView(R.id.fav_btn)
    FloatingActionButton favBtn;
    @BindView(R.id.descriptionTv)
    TextView descriptionTv;

    private Movie mMovie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        ButterKnife.bind(this);

        Movie movie = getIntent().getExtras().getParcelable(Constant.MOVIE_EXTRA);
        mMovie = movie;

        boolean isFav = Util.isFavourit(mMovie.getId());
        if(isFav){
            favBtn.setImageResource(R.drawable.star_selected);
        }else {
            favBtn.setImageResource(R.drawable.star);
        }

        titleTv.setText(movie.getTitle());

        // ratingTv.setText(String.valueOf(Math.getExponent(movie.getPopularity())));
        ratingTv.setText(String.format("%.1f", movie.getVoteAverage()));

        descriptionTv.setText(movie.getOverview());

        Picasso.get()
                .load(Constant.BASE_IMAGE_URL + movie.getBackdropPath())
                .into(coverImage);
        Picasso.get()
                .load(Constant.BASE_IMAGE_URL + movie.getPosterPath())
                .into(img);

        favBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean isFav = Util.isFavourit(mMovie.getId());
                if (isFav) {
                    Util.removeFromFav(mMovie.getId());
                    favBtn.setImageResource(R.drawable.star);
                    Toast.makeText(DetailActivity.this, "Remove From Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    Util.addToFav(mMovie);
                    favBtn.setImageResource(R.drawable.star_selected);
                    Toast.makeText(DetailActivity.this, "Add to Favourite ", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }
}
