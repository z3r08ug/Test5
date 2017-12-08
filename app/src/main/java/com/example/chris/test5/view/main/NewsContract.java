package com.example.chris.test5.view.main;

import com.example.chris.test5.model.NewsResponse;
import com.example.chris.test5.util.BasePresenter;
import com.example.chris.test5.util.BaseView;

import java.util.List;
import java.util.Map;


/**
 * Created by Admin on 11/29/2017.
 */

public interface NewsContract
{
    //methods for news activity
    interface View extends BaseView
    {
        void setNewsResponse(NewsResponse newsResponses);
        void showProgress(String progress);
    }

    interface Presenter extends BasePresenter<View>
    {
        void getNewsResponse(Map<String, String> map);
    }
}
