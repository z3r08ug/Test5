package com.example.chris.test5.di;

import com.example.chris.test5.view.main.NewsPresenter;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

/**
 * Created by Admin on 11/29/2017.
 */

@Module
public class NewsModule
{
    @Provides
    @Singleton
    NewsPresenter providerNewsPresenter()
    {
        return new NewsPresenter();
    }
}
