package com.widyan.alamku.adapters;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.widyan.alamku.R;
import com.widyan.alamku.models.AlamData;
import com.widyan.alamku.utils.Constants;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by widyan on 3/17/2017.
 */

public class HeaderBannerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder>{
    public final ArrayList<AlamData> data;
    private Context ctx;

    public HeaderBannerAdapter(Context ctx, ArrayList<AlamData> data) {
        this.ctx = ctx;
        this.data = data;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_header_adapter, parent, false);
        HeaderGridViewHolder headerGridViewHolder = new HeaderGridViewHolder(view);
        return  headerGridViewHolder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        HeaderGridViewHolder headerGridViewHolder = (HeaderGridViewHolder)holder;
        HashMap<String, String> file = new HashMap<String, String>();
        for( ; position < 5;position++){
            file.put(""+position, Constants.Apps.URL_IMAGE+data.get(position).getUrlImage().toString());
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
    }

    @Override
    public int getItemCount() {
        return data.size();
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
