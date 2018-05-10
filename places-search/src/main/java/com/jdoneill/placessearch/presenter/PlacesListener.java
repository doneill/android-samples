package com.jdoneill.placessearch.presenter;

import com.jdoneill.placessearch.model.Predictions;
import com.jdoneill.placessearch.model.Result;

import java.util.List;

public interface PlacesListener {

    void getPredictionsList(List<Predictions> predictions);

    void getResult(Result result);
}
