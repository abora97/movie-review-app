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

public class MainActivity extends AppCompatActivity  {





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }









}
