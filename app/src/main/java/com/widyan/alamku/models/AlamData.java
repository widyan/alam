package com.widyan.alamku.models;

/**
 * Created by widyan on 3/16/2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AlamData {

    @SerializedName("id_data")
    @Expose
    private Integer idData;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("url_image")
    @Expose
    private String urlImage;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("category")
    @Expose
    private String category;
    @SerializedName("Location")
    @Expose
    private String location;
    @SerializedName("rate")
    @Expose
    private Double rate;

    public Integer getIdData() {
        return idData;
    }

    public void setIdData(Integer idData) {
        this.idData = idData;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getRate() {
        return rate;
    }

    public void setRate(Double rate) {
        this.rate = rate;
    }

}
