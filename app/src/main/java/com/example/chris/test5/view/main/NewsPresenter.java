package com.example.chris.test5.view.main;

import android.util.Log;

import com.example.chris.test5.data.remote.RemoteDataSource;
import com.example.chris.test5.model.NewsResponse;

import java.util.Map;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Admin on 11/29/2017.
 */

public class NewsPresenter implements NewsContract.Presenter
{
    NewsContract.View view;
    public static final String TAG = NewsPresenter.class.getSimpleName() + "_TAG";
    NewsResponse news;

    @Override
    public void attachView(NewsContract.View view)
    {
        this.view = view;
    }

    @Override
    public void detachView()
    {

    }

    @Override
    public void getNewsResponse(Map<String, String> map)
    {
        RemoteDataSource.getNewsResponse(map)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NewsResponse>()
                {
                    @Override
                    public void onSubscribe(Disposable d)
                    {
                        view.showProgress("Downloading books...");
                    }

                    @Override
                    public void onNext(NewsResponse newsResponse)
                    {
                        Log.d(TAG, "onNext: ");
                        news = newsResponse;
                    }

                    @Override
                    public void onError(Throwable e)
                    {
                        Log.d(TAG, "onError: "+e.toString());
                        view.showError(e.toString());
                    }

                    @Override
                    public void onComplete()
                    {
                        Log.d(TAG, "onComplete: ");
                        view.setNewsResponse(news);
                        view.showProgress("Downloaded the news");
                    }
                });
    }


}
