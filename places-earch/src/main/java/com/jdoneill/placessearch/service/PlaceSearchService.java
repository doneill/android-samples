package com.jdoneill.placessearch.service;


import com.jdoneill.placessearch.model.Prediction;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;


public interface PlaceSearchService {

    @GET("api/place/autocomplete/json")
    Call<Prediction> getPredictions(@Query("key") String apikey, @Query("input") String input, @Query("location") String location, @Query("radius") String radius);

}
