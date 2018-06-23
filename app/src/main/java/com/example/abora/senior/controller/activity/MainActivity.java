package com.example.abora.senior.controller.activity;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.appizona.yehiahd.fastsave.FastSave;
import com.example.abora.senior.R;
import com.example.abora.senior.adapter.MoviesAdapter;
import com.example.abora.senior.api.Networking;
import com.example.abora.senior.callback.onMoviceRecivedListener;
import com.example.abora.senior.model.Movie;
import com.example.abora.senior.util.Connection;
import com.example.abora.senior.util.Constant;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements SwipeRefreshLayout.OnRefreshListener {

    @BindView(R.id.rec_movie)
    RecyclerView recMovie;
    @BindView(R.id.swpipe_layout)
    SwipeRefreshLayout swpipeLayout;

    private MoviesAdapter adapter;
    private RecyclerView.LayoutManager mLayoutManager;

    int ref = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mLayoutManager = new GridLayoutManager(this, 3);
        recMovie.setLayoutManager(mLayoutManager);

        swpipeLayout.setOnRefreshListener(this);

        if (Connection.isNetworkAvailable(this)) {
            getMovies(Constant.POPULAR_URL);
        } else {
            loadFromChach();
        }
    }

    private void loadFromChach() {
        List<Movie> cachedPoupler = FastSave.getInstance().getObjectsList(Constant.POP_MOVIE_CACHED, Movie.class);
        List<Movie> cachedtop = FastSave.getInstance().getObjectsList(Constant.TOP_MOVIE_CACHED, Movie.class);

        if (cachedPoupler.size() > 0) {
            adapter = new MoviesAdapter(this, cachedPoupler);
            recMovie.setAdapter(adapter);
        } else if (cachedtop.size() > 0) {
            adapter = new MoviesAdapter(this, cachedtop);
            recMovie.setAdapter(adapter);
        } else {
            Toast.makeText(this, "No Internet Connection", Toast.LENGTH_SHORT).show();
            getFav();
        }

    }

    private void getMovies(final String movieType) {
        Networking.getMovice(movieType, new onMoviceRecivedListener() {
            @Override
            public void onSuccess(List<Movie> movies) {
                adapter = new MoviesAdapter(MainActivity.this, movies);
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
                Toast.makeText(MainActivity.this, errorMsg, Toast.LENGTH_SHORT).show();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.my_menu, menu);
        return true;
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

    private void getFav() {
        List<Movie> movies = FastSave.getInstance().getObjectsList(Constant.FAV_MOVIE, Movie.class);
        adapter = new MoviesAdapter(this, movies);
        recMovie.setAdapter(adapter);
        if (swpipeLayout.isRefreshing())
            swpipeLayout.setRefreshing(false);
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
}
