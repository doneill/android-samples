package com.jdoneill.placessearch.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {

    @SerializedName("predictions")
    @Expose
    private List<Predictions> predictions = null;

    public List<Predictions> getPredictions() {
        return predictions;
    }

}
