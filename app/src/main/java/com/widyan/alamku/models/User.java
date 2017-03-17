package com.widyan.alamku.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by widyan on 3/15/2017.
 */

public class User {
    @SerializedName("data")
    @Expose
    private ArrayList<UserData> data = null;

    public ArrayList<UserData> getData() {
        return data;
    }

    public void setData(ArrayList<UserData> data) {
        this.data = data;
    }

}
