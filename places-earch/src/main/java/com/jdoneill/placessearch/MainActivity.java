package com.jdoneill.placessearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jdoneill.placessearch.model.Predictions;
import com.jdoneill.placessearch.presenter.PlaceAutocomplete;
import com.jdoneill.placessearch.presenter.PredictionsListener;

import java.util.List;


public class MainActivity extends AppCompatActivity implements PredictionsListener {

    private PlaceAutocomplete placeAutocomplete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placeAutocomplete = new PlaceAutocomplete(this, this);
        placeAutocomplete.getPredictions();

    }

    @Override
    public void getPredictionsList(List<Predictions> predictions) {
        for(Predictions p : predictions){
            Log.d("Places", "Place: " + p.getStructuredFormatting().getMainText());
            Log.d("Places", "Description: " + p.getDescription());
        }
    }
}
