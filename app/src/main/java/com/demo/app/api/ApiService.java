package com.demo.app.api;

import com.demo.app.api.service.CardService;
import com.demo.app.util.Constents;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Created by LXG on 2016/12/9.
 */

public class ApiService {
    private static final String baseUrl = Constents.REQUEST_URL + "/api/";

    public static CardService createCardService() {
        return Retrofit().create(CardService.class);
    }

    /**
     * 非定制retrofit
     */
    private static Retrofit Retrofit() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseUrl)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        return retrofit;
    }
}
