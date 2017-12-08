package com.example.chris.test5.di;

import com.example.chris.test5.NewsActivity;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by Admin on 11/29/2017.
 */

@Component(modules = NewsModule.class)
@Singleton
public interface NewsComponent
{

    void inject(NewsActivity newsActivity);
}
