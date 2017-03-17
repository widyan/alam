package com.widyan.alamku;

import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.widyan.alamku.adapters.GridAlamkuAdapter;
import com.widyan.alamku.customs.ItemClickSupport;
import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.models.Alam;
import com.widyan.alamku.models.AlamData;
import com.widyan.alamku.utils.Constants;
import com.widyan.alamku.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AlamkuActivity extends AppCompatActivity {

    Spinner spinner_kategori;
    SwipeRefreshLayout swipe_refresh_layout;
    RecyclerView recycleview_alamku;
    GridAlamkuAdapter adapter;
    ImageView btn_tambah_alam;
    private APIServices mAPIService;
    private ArrayList<AlamData> dataAlam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alamku);

        spinner_kategori = (Spinner) findViewById(R.id.spinner_kategori);
        recycleview_alamku = (RecyclerView) findViewById(R.id.recycleview_alamku);
        swipe_refresh_layout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh_layout);
        btn_tambah_alam = (ImageView) findViewById(R.id.btn_tambah_alam);

        btn_tambah_alam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startThisActivity(AlamkuActivity.this, InputAlamkuActivity.class);
            }
        });

        mAPIService = Utils.getAPIServiceGenerator();
        dataAlam = new ArrayList<AlamData>();

        spinner_kategori.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                final int kode_kategori = setDataSpinner(spinner_kategori.getSelectedItem().toString());
                Log.i("ALAMKU", "KODE = " + kode_kategori);
                swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getFilterAlamDao(kode_kategori);
                    }
                });
                swipe_refresh_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        getFilterAlamDao(kode_kategori);
                    }
                });
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
                swipe_refresh_layout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        getListAlam();
                    }
                });
                swipe_refresh_layout.post(new Runnable() {
                    @Override
                    public void run() {
                        getListAlam();
                    }
                });
            }
        });
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

    public void setDataGrid(final ArrayList<AlamData> dataAlam) {
        adapter = new GridAlamkuAdapter(AlamkuActivity.this, dataAlam);

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
