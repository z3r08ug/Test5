package com.example.chris.test5.data.remote;

import com.example.chris.test5.model.NewsResponse;

import java.util.List;
import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.QueryMap;

/**
 * Created by Admin on 11/29/2017.
 */

public interface RemoteService
{
    @GET("top-headlines")
    Observable<NewsResponse> getNewsResponse(@QueryMap Map<String, String> map);
    
}
