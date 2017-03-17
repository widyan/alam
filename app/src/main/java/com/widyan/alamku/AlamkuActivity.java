package com.widyan.alamku;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.widyan.alamku.adapters.GridAlamkuAdapter;
import com.widyan.alamku.adapters.HeaderBannerAdapter;
import com.widyan.alamku.customs.CustomTextViewLatoLight;
import com.widyan.alamku.customs.CustomTextViewLatoRegular;
import com.widyan.alamku.customs.ItemClickSupport;
import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.models.Alam;
import com.widyan.alamku.models.AlamData;
import com.widyan.alamku.utils.Constants;
import com.widyan.alamku.utils.SharedPrefData;
import com.widyan.alamku.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlamkuActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener{

    Toolbar toolbar_alamku;
    Spinner spinner_kategori;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recycleview_alamku;
    GridAlamkuAdapter adapter;
    HeaderBannerAdapter bannerAdapter;
    private Menu mOptionMenu;
    CustomTextViewLatoRegular txt_banner_judul;
    CustomTextViewLatoLight txt_banner_deskripsi, txt_banner_created_at;
    private APIServices mAPIService;
    private ArrayList<AlamData> dataAlam;
    private ArrayList<AlamData> banner;
    private SliderLayout mSlider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamku);

        invalidateOptionsMenu();

        toolbar_alamku = (Toolbar)findViewById(R.id.toolbar_alamku);
        setSupportActionBar(toolbar_alamku);

        spinner_kategori = (Spinner) findViewById(R.id.spinner_kategori);
        recycleview_alamku = (RecyclerView) findViewById(R.id.recycleview_alamku);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        txt_banner_judul = (CustomTextViewLatoRegular)findViewById(R.id.txt_banner_judul);
        txt_banner_deskripsi = (CustomTextViewLatoLight) findViewById(R.id.txt_banner_deskripsi);
        txt_banner_created_at = (CustomTextViewLatoLight)findViewById(R.id.txt_banner_created_at);

        mAPIService = Utils.getAPIServiceGenerator();
        dataAlam = new ArrayList<AlamData>();
        int kode_kategori = setDataSpinner(spinner_kategori.getSelectedItem().toString());
        spinner_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final int kode_kategori = setDataSpinner(spinner_kategori.getSelectedItem().toString());
                Log.i("ALAMKU", "KODE = " + kode_kategori);
                swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        refreshBanner();
                        getFilterAlamDao(kode_kategori);
                        //getBannerAlamDao(kode_kategori);
                    }
                });
                swipe_refresh_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        refreshBanner();
                        getFilterAlamDao(kode_kategori);
                        //getBannerAlamDao(kode_kategori);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getListAlam();
                        //getBannerAlamDao(kode_kategori);
                    }
                });
                swipe_refresh_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        getListAlam();
                        //getBannerAlamDao(kode_kategori);
                    }
                });
            }
        });
        getBannerAlamDao(kode_kategori);
    }

    @Override
    public boolean onCreateOptionsMenu(final Menu menu) {
        final MenuItem[] item = new MenuItem[2];
        getMenuInflater().inflate(R.menu.menu_detail_alamku, menu);
        this.mOptionMenu = menu;
        item[0] = menu.findItem(R.id.action_tambah_data).setVisible(true);
        item[1] = menu.findItem(R.id.action_logout).setVisible(true);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if(id == R.id.action_tambah_data){
            Utils.startThisActivity(AlamkuActivity.this, InputAlamkuActivity.class);
        }
        if(id == R.id.action_logout){
            SharedPrefData.Logout(this);
            Utils.startThisActivity(AlamkuActivity.this, LoginActivity.class);
        }
        return super.onOptionsItemSelected(item);
    }

    public void refreshBanner(){
        try {
            if(!banner.isEmpty()){
                banner.clear();
            }
        }catch (NullPointerException e){
            e.printStackTrace();
        }
    }

    public int setDataSpinner(String kategori) {
        int kode_kategori = 1;
        switch (kategori) {
            case "Datarang Tinggi":
                kode_kategori = 1;
                break;
            case "Dataran Rendah":
                kode_kategori = 2;
                break;
            case "Pantai":
                kode_kategori = 3;
                break;
        }
        return kode_kategori;
    }

    public void getBannerAlamDao(int kategori) {
        swipe_refresh_layout.setRefreshing(true);
        Log.i("ALAMKU", Constants.Apps.BANNER);
        banner = new ArrayList<AlamData>();
        mAPIService.getBanner(kategori).enqueue(new Callback<Alam>() {
            @Override
            public void onResponse(Call<Alam> call, Response<Alam> response) {
                //Log.i("ALAMKU", "FILTER LIST DATA ALAM = " + response.body().getData().get(0).getTitle());
                if (response.isSuccessful()) {
                    Log.i("ALAMKU", "SUCCESS GET BANNER DATA ALAM = " + response.body().toString());
                    banner = response.body().getData();
                    setDataBanner(banner);
                } else {
                    Log.i("ALAMKU", "ERR = " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Alam> call, Throwable t) {
                t.printStackTrace();
                Log.e("ALAMKU ERR", "Unable to submit GET to API");
            }
        });
    }

    public void getFilterAlamDao(int kategori) {
        swipe_refresh_layout.setRefreshing(true);
        Log.i("ALAMKU", Constants.Apps.FILTER_TEMPAT_WISATA);

        mAPIService.getFilterAlamDao(kategori).enqueue(new Callback<Alam>() {
            @Override
            public void onResponse(Call<Alam> call, Response<Alam> response) {
                //Log.i("ALAMKU", "FILTER LIST DATA ALAM = " + response.body().getData().get(0).getTitle());
                if (response.isSuccessful()) {
                    Log.i("ALAMKU", "SUCCESS GET FILTER LIST DATA ALAM = " + response.body().toString());
                    dataAlam = response.body().getData();
                    setDataGrid(dataAlam);
                } else {
                    Log.i("ALAMKU", "ERR = " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Alam> call, Throwable t) {
                t.printStackTrace();
                Log.e("ALAMKU ERR", "Unable to submit GET to API");
            }
        });
    }

    public void getListAlam() {
        swipe_refresh_layout.setRefreshing(true);
        Log.i("ALAMKU", Constants.Apps.LIST_TEMPAT_WISATA);

        mAPIService.getListAlam().enqueue(new Callback<Alam>() {
            @Override
            public void onResponse(Call<Alam> call, Response<Alam> response) {
                Log.i("ALAMKU", "LIST DATA ALAM = " + response.body().getData().get(0).getTitle());
                if (response.isSuccessful()) {
                    Log.i("ALAMKU", "SUCCESS GET LIST DATA ALAM = " + response.body().toString());
                    dataAlam = response.body().getData();
                    setDataGrid(dataAlam);
                } else {
                    Log.i("ALAMKU", "ERR = " + response.body().toString());
                }
            }

            @Override
            public void onFailure(Call<Alam> call, Throwable t) {
                t.printStackTrace();
                Log.e("ALAMKU ERR", "Unable to submit GET to API");
            }
        });
    }

    public void setDataBanner(final ArrayList<AlamData> dataAlam){
        mSlider = (SliderLayout)findViewById(R.id.slider);
        //bannerAdapter = new HeaderBannerAdapter(AlamkuActivity.this, dataAlam);
        HashMap<String, String> file = new HashMap<String, String>();
        for(int position=0; position < 5;position++){
            file.put(dataAlam.get(position).getTitle().toString(), Constants.Apps.URL_IMAGE+dataAlam.get(position).getUrlImage().toString());
        }

        for(String name : file.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            textSliderView
                    .image(file.get(name))
                    .setScaleType(BaseSliderView.ScaleType.CenterCrop)
                    .setOnSliderClickListener(this);
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle().putString("extra", name);

            mSlider.addSlider(textSliderView);
        }

        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Center_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.setDuration(4000);
        mSlider.setIndicatorVisibility(PagerIndicator.IndicatorVisibility.Visible);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {

    }

    public void setDataGrid(final ArrayList<AlamData> dataAlam) {
        adapter = new GridAlamkuAdapter(AlamkuActivity.this, dataAlam);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        GridLayoutManager gridLayoutManager = new GridLayoutManager(AlamkuActivity.this, 2);
        recycleview_alamku.setLayoutManager(gridLayoutManager);
        recycleview_alamku.setHasFixedSize(true);
        recycleview_alamku.setAdapter(adapter);
        swipe_refresh_layout.setRefreshing(false);
        ItemClickSupport.addTo(recycleview_alamku).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                Utils.startThisActivityWithParamString(AlamkuActivity.this, DetailAlamkuActivity.class, dataAlam.get(position).getIdData().toString(), Constants.KEY.KEY_ID_ALAMKU);
            }
        });
    }
}
