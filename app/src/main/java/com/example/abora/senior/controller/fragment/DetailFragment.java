package com.example.abora.senior.controller.fragment;


import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.abora.senior.R;
import com.example.abora.senior.controller.activity.DetailActivity;
import com.example.abora.senior.model.Movie;
import com.example.abora.senior.util.Constant;
import com.example.abora.senior.util.Util;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends Fragment {


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

    public DetailFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_detail, container, false);
     ButterKnife.bind(this, view);

        Movie movie = getActivity().getIntent().getExtras().getParcelable(Constant.MOVIE_EXTRA);
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
                    Toast.makeText(getActivity(), "Remove From Favourite", Toast.LENGTH_SHORT).show();
                } else {
                    Util.addToFav(mMovie);
                    favBtn.setImageResource(R.drawable.star_selected);
                    Toast.makeText(getActivity(), "Add to Favourite ", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
