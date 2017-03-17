package com.widyan.alamku.adapters;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Point;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RatingBar;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.squareup.picasso.Picasso;
import com.widyan.alamku.R;
import com.widyan.alamku.customs.CustomTextViewLatoRegular;
import com.widyan.alamku.models.Alam;
import com.widyan.alamku.models.AlamData;
import com.widyan.alamku.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by widyan on 3/16/2017.
 */

public class GridAlamkuAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    public final ArrayList<AlamData> data;
    private Context ctx;

    public GridAlamkuAdapter(Context ctx, ArrayList<AlamData> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
        GridViewHolder gridViewHolder = new GridViewHolder(layoutView);
        return gridViewHolder;

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        GridViewHolder gridViewHolder = (GridViewHolder) holder;
        WindowManager windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = (size.x) / 2;

        gridViewHolder.txt_item_alamku.setText(data.get(position).getTitle().toString());
        Picasso.with(ctx).load(Constants.Apps.URL_IMAGE + data.get(position).getUrlImage().toString()).resize(width, width).into(gridViewHolder.img_item_alamku);
        gridViewHolder.rate_bar_item_alamku.setRating(Float.parseFloat(data.get(position).getRate().toString()));
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_item_alamku;
        public CustomTextViewLatoRegular txt_item_alamku;
        public RatingBar rate_bar_item_alamku;
        public Resources res;

        public GridViewHolder(View itemView) {
            super(itemView);
            img_item_alamku = (ImageView) itemView.findViewById(R.id.img_item_alamku);
            txt_item_alamku = (CustomTextViewLatoRegular) itemView.findViewById(R.id.txt_item_alamku);
            rate_bar_item_alamku = (RatingBar) itemView.findViewById(R.id.rate_bar_item_alamku);
        }
    }
}
