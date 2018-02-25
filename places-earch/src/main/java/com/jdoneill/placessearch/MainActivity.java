package com.jdoneill.placessearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jdoneill.placessearch.service.PlaceSearchService;
import com.jdoneill.placessearch.model.Prediction;
import com.jdoneill.placessearch.model.Predictions;

import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    static final String APIKEY = BuildConfig.API_KEY;
    static final String BASE_URL = "https://maps.googleapis.com/maps/";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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

        PlaceSearchService placeSearchService = retrofit.create(PlaceSearchService.class);

        placeSearchService.getPredictions(APIKEY, "Safeway", "47.498277,-121.783975", "500").enqueue(new Callback<Prediction>() {
            @Override
            public void onResponse(Call<Prediction> call, Response<Prediction> response) {
                if (response.isSuccessful()){
                    Prediction prediction = response.body();
                    List<Predictions> predictions =  prediction.getPredictions();

                    for(Predictions p : predictions){
                        Log.d("Places", "Place: " + p.getStructuredFormatting().getMainText());
                        Log.d("Places", "Description: " + p.getDescription());
                    }
                }

            }

            @Override
            public void onFailure(Call<Prediction> call, Throwable t) {

            }
        });



    }

}
