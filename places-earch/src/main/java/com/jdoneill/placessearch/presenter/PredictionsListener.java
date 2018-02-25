package com.jdoneill.placessearch.presenter;

import com.jdoneill.placessearch.model.Predictions;

import java.util.List;

public interface PredictionsListener {

    void getPredictionsList(List<Predictions> predictions);
}
