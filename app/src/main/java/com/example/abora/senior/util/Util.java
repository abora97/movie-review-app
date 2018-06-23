package com.example.abora.senior.util;

import com.appizona.yehiahd.fastsave.FastSave;
import com.example.abora.senior.model.Movie;

import java.util.List;

public class Util {
    public static boolean isFavourit(int id){
        List<Movie> movies= FastSave.getInstance().getObjectsList(Constant.FAV_MOVIE,Movie.class);
        for (Movie m:movies){
            if(m.getId()==id){
                return true;
            }
        }
        return false;
    }
    public static void removeFromFav(int id){
        List<Movie> movies= FastSave.getInstance().getObjectsList(Constant.FAV_MOVIE,Movie.class);
        for (int i = 0; i <movies.size() ; i++) {
            if(movies.get(i).getId()==id){
                movies.remove(i);
                FastSave.getInstance().saveObjectsList(Constant.FAV_MOVIE,movies);
            }
        }
    }
    public static void addToFav(Movie mMovie){
        List<Movie> movies= FastSave.getInstance().getObjectsList(Constant.FAV_MOVIE,Movie.class);
        movies.add(mMovie);
        FastSave.getInstance().saveObjectsList(Constant.FAV_MOVIE,movies);

    }
}
