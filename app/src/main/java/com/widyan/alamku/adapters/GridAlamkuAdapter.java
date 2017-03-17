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

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;

    public final ArrayList<AlamData> data;
    public final ArrayList<AlamData> banner;
    private Context ctx;
    public GridAlamkuAdapter(Context ctx, ArrayList<AlamData> data, ArrayList<AlamData> banner) {
        this.ctx = ctx;
        this.data = data;
        this.banner = banner;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType == TYPE_HEADER){
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_adapter, parent, false);
            HeaderGridViewHolder headerGridViewHolder = new HeaderGridViewHolder(view);
            return  headerGridViewHolder;
        }else {
            View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_adapter, parent, false);
            GridViewHolder gridViewHolder = new GridViewHolder(layoutView);
            return gridViewHolder;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if(holder instanceof HeaderGridViewHolder){
            HeaderGridViewHolder headerGridViewHolder = (HeaderGridViewHolder)holder;
            HashMap<String, String> file = new HashMap<String, String>();
            for(int i = 0 ; i < 5;i++){
                file.put(""+i, Constants.Apps.URL_IMAGE+data.get(i).getUrlImage().toString());
            }

            for(String name : file.keySet()){
                TextSliderView textSliderView = new TextSliderView(ctx);
                textSliderView
                        .image(file.get(name))
                        .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                        .setOnSliderClickListener(headerGridViewHolder);
                textSliderView.bundle(new Bundle());
                textSliderView.getBundle().putString("extra", name);

                headerGridViewHolder.mSlider.addSlider(textSliderView);
            }

            headerGridViewHolder.mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
            headerGridViewHolder.mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
            //headerGridViewHolder.mSlider.setCustomAnimation(new Chi);
            headerGridViewHolder.mSlider.setDuration(3000);
        }else {
            GridViewHolder gridViewHolder = (GridViewHolder)holder;
            WindowManager windowManager = (WindowManager) ctx.getSystemService(Context.WINDOW_SERVICE);
            Display display = windowManager.getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            int width = (size.x)/2;

            gridViewHolder.txt_item_alamku.setText(data.get(position).getTitle().toString());
            Picasso.with(ctx).load(Constants.Apps.URL_IMAGE+data.get(position).getUrlImage().toString()).resize(width, width).into(gridViewHolder.img_item_alamku);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position))
            return TYPE_HEADER;
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position){
        return position == 0;
    }

    @Override
    public int getItemCount() {
        return data.size()+1;
    }

    public class GridViewHolder extends RecyclerView.ViewHolder {
        public ImageView img_item_alamku;
        public CustomTextViewLatoRegular txt_item_alamku;
        public RatingBar rate_bar_item_alamku;
        public Resources res;
        public GridViewHolder(View itemView) {
            super(itemView);
            img_item_alamku = (ImageView)itemView.findViewById(R.id.img_item_alamku);
            txt_item_alamku = (CustomTextViewLatoRegular)itemView.findViewById(R.id.txt_item_alamku);
            rate_bar_item_alamku = (RatingBar)itemView.findViewById(R.id.rate_bar_item_alamku);
        }
    }

    class HeaderGridViewHolder extends RecyclerView.ViewHolder implements BaseSliderView.OnSliderClickListener{
        public SliderLayout mSlider;

        public HeaderGridViewHolder(View itemView) {
            super(itemView);
            this.mSlider = (SliderLayout)itemView.findViewById(R.id.slider);
        }

        @Override
        public void onSliderClick(BaseSliderView slider) {

        }
    }
}
