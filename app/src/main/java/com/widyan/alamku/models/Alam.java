package com.widyan.alamku.models;

/**
 * Created by widyan on 3/16/2017.
 */

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Alam {

    @SerializedName("data")
    @Expose
    private ArrayList<AlamData> data = null;

    public ArrayList<AlamData> getData() {
        return data;
    }

    public void setData(ArrayList<AlamData> data) {
        this.data = data;
    }

}
