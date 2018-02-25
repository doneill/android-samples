
package com.jdoneill.placessearch.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Prediction {

    @SerializedName("predictions")
    @Expose
    private List<Predictions> predictions = null;
    @SerializedName("status")
    @Expose
    private String status;

    public List<Predictions> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<Predictions> predictions) {
        this.predictions = predictions;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
