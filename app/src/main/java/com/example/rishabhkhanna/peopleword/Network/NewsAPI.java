package com.example.rishabhkhanna.peopleword.Network;

import com.example.rishabhkhanna.peopleword.Network.interfaces.getNews;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by rishabhkhanna on 21/07/17.
 */

public class NewsAPI {
    private static NewsAPI api = null;
    public getNews getNews;

    private NewsAPI() {
        getNews = new Retrofit.Builder()
                .baseUrl("http://172.16.109.190:9890/")
                .addConverterFactory(
                        GsonConverterFactory.create()
                )
                .build()
                .create(getNews.class);

    }

    public static NewsAPI getInstance(){
        if(api == null){
            api = new NewsAPI();
        }
        return api;
    }
}
