package com.example.abora.senior.api;


import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;

import com.example.abora.senior.callback.onMoviceRecivedListener;
import com.example.abora.senior.model.MovieResponse;
import com.example.abora.senior.util.Constant;

public class Networking {
    public static void getMovice(String moviceType,final onMoviceRecivedListener listener) {

        // to connect to with api (movie DB)
        AndroidNetworking.get(Constant.BASE_URL + moviceType)
                .addQueryParameter(Constant.API_KEY, Constant.API_KEY_VALUE)
                .addQueryParameter(Constant.PAGE_KEY, "1")
                .build()
                .getAsObject(MovieResponse.class, new ParsedRequestListener<MovieResponse>() {

                    @Override
                    public void onResponse(MovieResponse response) {
                        listener.onSuccess(response.getMovies());
                    }

                    @Override
                    public void onError(ANError anError) {
                        listener.onError(anError.getErrorDetail());

                    }
                });

    }
}
