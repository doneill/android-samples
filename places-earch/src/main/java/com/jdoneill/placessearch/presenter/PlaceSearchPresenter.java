package com.jdoneill.placessearch.presenter;

import android.content.Context;

import com.jdoneill.placessearch.BuildConfig;
import com.jdoneill.placessearch.model.Prediction;
import com.jdoneill.placessearch.service.PlaceSearchService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceSearchPresenter {
    static final String APIKEY = BuildConfig.API_KEY;

    private final Context context;
    private final PredictionsListener mListener;
    private final PlaceSearchService placeSearchService;


    public PlaceSearchPresenter(PredictionsListener listener, Context context){
        this.mListener = listener;
        this.context = context;
        this.placeSearchService = new PlaceSearchService();
    }

    public void getPredictions(){
        placeSearchService
                .getAPI()
                .getPredictions(APIKEY, "Safeway", "47.498277,-121.783975", "500")
                .enqueue(new Callback<Prediction>() {
            @Override
            public void onResponse(Call<Prediction> call, Response<Prediction> response) {
                if (response.isSuccessful()){
                    Prediction result = response.body();
                    if(result != null){
                        mListener.getPredictionsList(result.getPredictions());
                    }
                }

            }

            @Override
            public void onFailure(Call<Prediction> call, Throwable t) {
                try {
                    throw new InterruptedException("Error communicating with the server");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
