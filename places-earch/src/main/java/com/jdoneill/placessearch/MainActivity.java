package com.jdoneill.placessearch;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.jdoneill.placessearch.model.Predictions;
import com.jdoneill.placessearch.presenter.PlaceSearchPresenter;

import java.util.List;


public class MainActivity extends AppCompatActivity implements PlaceSearchPresenter.PlaceSearchPresenterListener{

    private PlaceSearchPresenter placeSearchPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        placeSearchPresenter = new PlaceSearchPresenter(this, this);
        placeSearchPresenter.getPredictions();

    }

    @Override
    public void placeSearchReady(List<Predictions> predictions) {
        for(Predictions p : predictions){
            Log.d("Places", "Place: " + p.getStructuredFormatting().getMainText());
            Log.d("Places", "Description: " + p.getDescription());
        }
    }
}
