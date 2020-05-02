package com.example.sub3.listtvshow;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.ParsedRequestListener;
import com.example.sub3.MovieCatalogue;
import com.example.sub3.listtvshow.pojo.ResponseTvShows;

public class ListTvShowViewModel  extends ViewModel {
    private MutableLiveData<ResponseTvShows> responseTvShows = new MutableLiveData<>();

    MutableLiveData getTvShowList() {
        if (responseTvShows == null) {
            doRequestListTvShows();
        }
        return responseTvShows;
    }

    void doRequestListTvShows() {
        AndroidNetworking.get("https://api.themoviedb.org/3/discover/tv")
                .addQueryParameter("api_key", MovieCatalogue.MOVIE_DB_API_KEY)
                .addQueryParameter("language", "en-US")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsObject(ResponseTvShows.class, new ParsedRequestListener<ResponseTvShows>() {
                    @Override
                    public void onResponse(ResponseTvShows response) {
                        responseTvShows.postValue(response);

                    }

                    @Override
                    public void onError(ANError anError) {
                        responseTvShows.setValue(new ResponseTvShows(anError));
                    }
                });
    }
}
