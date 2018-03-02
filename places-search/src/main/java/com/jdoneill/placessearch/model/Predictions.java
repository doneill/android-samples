package com.jdoneill.placessearch.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Predictions {

    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("place_id")
    @Expose
    private String placeId;
    @SerializedName("reference")
    @Expose
    private String reference;
    @SerializedName("structured_formatting")
    @Expose
    private StructuredFormatting structuredFormatting;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlaceId() {
        return placeId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }

    public StructuredFormatting getStructuredFormatting() {
        return structuredFormatting;
    }

}
