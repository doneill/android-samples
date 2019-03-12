package com.jdoneill.placessearch.presenter;

import com.jdoneill.placessearch.BuildConfig;
import com.jdoneill.placessearch.model.PlaceDetails;
import com.jdoneill.placessearch.model.Prediction;
import com.jdoneill.placessearch.service.PlaceService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PlaceAutocomplete {
    private static final String APIKEY = BuildConfig.API_KEY;
    
    private final PlacesListener mListener;
    private final PlaceService placeService;


    public PlaceAutocomplete(PlacesListener listener){
        this.mListener = listener;
        this.placeService = new PlaceService();
    }

    public void getPredictions(String text, String latLng){
        placeService
                .getAPI()
                .getPredictions(APIKEY, text, latLng, "500")
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

    public void getResultFromPlaceId(String placeId){
        placeService
                .getAPI()
                .getDetails(APIKEY, placeId)
                .enqueue(new Callback<PlaceDetails>() {
            @Override
            public void onResponse(Call<PlaceDetails> call, Response<PlaceDetails> response) {
                if(response.isSuccessful()){
                    PlaceDetails placeDetails = response.body();
                    if(placeDetails != null){
                        mListener.getResult(placeDetails.getResult());
                    }
                }
            }

            @Override
            public void onFailure(Call<PlaceDetails> call, Throwable t) {
                try {
                    throw new InterruptedException("Error communicating with the server");
                }catch (InterruptedException e){
                    e.printStackTrace();
                }
            }
        });
    }

}
