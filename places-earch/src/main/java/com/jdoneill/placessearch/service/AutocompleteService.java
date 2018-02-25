package com.jdoneill.placessearch.service;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jdoneill.placessearch.model.Prediction;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;


public class AutocompleteService {
    static final String BASE_URL = "https://maps.googleapis.com/maps/";

    public interface AutomcompleteAPI {

        @GET("api/place/autocomplete/json")
        Call<Prediction> getPredictions(@Query("key") String apikey, @Query("input") String input, @Query("location") String location, @Query("radius") String radius);

    }

    public AutomcompleteAPI getAPI(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(client)
                .build();

        return retrofit.create(AutomcompleteAPI.class);
    }
}


