package com.example.abora.senior.controller.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.appizona.yehiahd.fastsave.FastSave;
import com.example.abora.senior.R;
import com.example.abora.senior.adapter.MoviesAdapter;
import com.example.abora.senior.api.Networking;
import com.example.abora.senior.callback.onMoviceRecivedListener;
import com.example.abora.senior.controller.activity.MainActivity;
import com.example.abora.senior.model.Movie;
import com.example.abora.senior.util.Connection;
import com.example.abora.senior.util.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements SwipeRefreshLayout.OnRefreshListener {


    @BindView(R.id.rec_movie)
    RecyclerView recMovie;
    @BindView(R.id.swpipe_layout)
    SwipeRefreshLayout swpipeLayout;

    private MoviesAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int ref = 0;

    public MainFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_main, container, false);
        ButterKnife.bind(this, view);

        mLayoutManager = new GridLayoutManager(getActivity(), 3);
        recMovie.setLayoutManager(mLayoutManager);

        swpipeLayout.setOnRefreshListener(this);

        if (Connection.isNetworkAvailable(getActivity())) {
            getMovies(Constant.POPULAR_URL);
        } else {
            loadFromChach();
        }
        return view;
    }

    private void loadFromChach() {
        List<Movie> cachedPoupler = FastSave.getInstance().getObjectsList(Constant.POP_MOVIE_CACHED, Movie.class);
        List<Movie> cachedtop = FastSave.getInstance().getObjectsList(Constant.TOP_MOVIE_CACHED, Movie.class);

        if (cachedPoupler.size() > 0) {
            adapter = new MoviesAdapter(getActivity(), cachedPoupler);
            recMovie.setAdapter(adapter);
        } else if (cachedtop.size() > 0) {
            adapter = new MoviesAdapter(getActivity(), cachedtop);
            recMovie.setAdapter(adapter);
        } else {
            Toast.makeText(getActivity(), "No Internet Connection", Toast.LENGTH_SHORT).show();
            getFav();
        }

    }

    private void getMovies(final String movieType) {
        Networking.getMovice(movieType, new onMoviceRecivedListener() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MoviesAdapter(getActivity(), movies);
                recMovie.setAdapter(adapter);

                if (swpipeLayout.isRefreshing())
                    swpipeLayout.setRefreshing(false);

                if (movieType.equals(Constant.POPULAR_URL)) {
                    FastSave.getInstance().saveObjectsList(Constant.POP_MOVIE_CACHED, movies);
                } else {
                    FastSave.getInstance().saveObjectsList(Constant.TOP_MOVIE_CACHED, movies);
                }
            }

            @Override
            public void onError(String errorMsg) {
                Toast.makeText(getActivity(), errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    private void getFav() {
        List<Movie> movies = FastSave.getInstance().getObjectsList(Constant.FAV_MOVIE, Movie.class);
        adapter = new MoviesAdapter(getActivity(), movies);
        recMovie.setAdapter(adapter);
        if (swpipeLayout.isRefreshing())
            swpipeLayout.setRefreshing(false);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.my_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mu_pop:
                ref = 0;
                getMovies(Constant.POPULAR_URL);
                break;
            case R.id.mu_top:
                ref = 1;
                getMovies(Constant.TOP_URL);
                break;
            case R.id.mu_fav:
                ref = 2;
                getFav();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onRefresh() {
        switch (ref) {
            case 0:
                getMovies(Constant.POPULAR_URL);
                break;
            case 1:
                getMovies(Constant.TOP_URL);
                break;
            case 2:
                getFav();
                break;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
