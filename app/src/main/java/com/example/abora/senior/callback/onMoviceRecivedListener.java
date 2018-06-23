package com.example.abora.senior.callback;

import com.example.abora.senior.model.Movie;

import java.util.List;

public interface onMoviceRecivedListener {
    void onSuccess(List<Movie> movies);

    void onError(String errorMsg);
}
