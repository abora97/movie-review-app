package com.example.abora.senior.application;

import android.app.Application;

import com.androidnetworking.AndroidNetworking;
import com.appizona.yehiahd.fastsave.FastSave;
import com.example.abora.senior.util.Constant;

import java.util.ArrayList;

public class MoviesApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        // connection use android fast network library
        AndroidNetworking.initialize(getApplicationContext());
        // to internal storage
        FastSave.init(getApplicationContext());

        if (!FastSave.getInstance().isKeyExists(Constant.FAV_MOVIE))
            FastSave.getInstance().saveObjectsList(Constant.FAV_MOVIE, new ArrayList<>());

        if (!FastSave.getInstance().isKeyExists(Constant.POP_MOVIE_CACHED))
            FastSave.getInstance().saveObjectsList(Constant.POP_MOVIE_CACHED, new ArrayList<>());

        if (!FastSave.getInstance().isKeyExists(Constant.TOP_MOVIE_CACHED))
            FastSave.getInstance().saveObjectsList(Constant.TOP_MOVIE_CACHED, new ArrayList<>());
    }
}
