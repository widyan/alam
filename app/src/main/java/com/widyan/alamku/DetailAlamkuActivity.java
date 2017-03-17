package com.widyan.alamku;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.widyan.alamku.customs.CustomTextViewBoldRoboto;
import com.widyan.alamku.customs.CustomTextViewLatoLight;
import com.widyan.alamku.customs.CustomTextViewLatoRegular;
import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.models.Alam;
import com.widyan.alamku.models.AlamData;
import com.widyan.alamku.utils.Constants;
import com.widyan.alamku.utils.Utils;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailAlamkuActivity extends AppCompatActivity {
    TextView txt_title_tempat_alamku;
    ImageView img_detail_alamku, btn_back;
    CustomTextViewLatoRegular txt_detail_nama_alam;
    CustomTextViewLatoLight txt_lokasi_alam, txt_created_at_alam, txt_deskripsi_alamku;
    CustomTextViewBoldRoboto txt_rating_alamku;
    RatingBar rate_bar_alamku;
    private APIServices mAPIService;
    private ArrayList<AlamData> dataAlam;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_alamku);

        mAPIService = Utils.getAPIServiceGenerator();
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent i = getIntent();
        String id = i.getStringExtra(Constants.KEY.KEY_ID_ALAMKU);
        Log.i("ALAMKU", "ID nya = " + id);
        Toast.makeText(this, "ID nya wlee = " + id,Toast.LENGTH_LONG).show();

        dataAlam = new ArrayList<AlamData>();

        txt_title_tempat_alamku = (TextView)findViewById(R.id.txt_title_tempat_alamku);
        txt_detail_nama_alam = (CustomTextViewLatoRegular) findViewById(R.id.txt_detail_nama_alam);
        txt_lokasi_alam = (CustomTextViewLatoLight)findViewById(R.id.txt_lokasi_alam);
        txt_created_at_alam = (CustomTextViewLatoLight)findViewById(R.id.txt_created_at_alam);
        txt_deskripsi_alamku = (CustomTextViewLatoLight)findViewById(R.id.txt_deskripsi_alamku);
        txt_rating_alamku = (CustomTextViewBoldRoboto)findViewById(R.id.txt_rating_alamku);

        rate_bar_alamku = (RatingBar)findViewById(R.id.rate_bar_alamku);
        img_detail_alamku = (ImageView)findViewById(R.id.img_detail_alamku);
        btn_back = (ImageView)findViewById(R.id.btn_back);

        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Log.i("ALAMKU", "ID nya = " + Integer.parseInt(id));
        getDetailAlam(Integer.parseInt(id));
        /*FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }

    public void getDetailAlam(int id) {
        mAPIService.getDetailAlam(id).enqueue(new Callback<Alam>() {
            @Override
            public void onResponse(Call<Alam> call, Response<Alam> response) {
                Log.i("ALAMKU", "DETAIL DATA ALAM = " + response.body().getData().get(0).getTitle());
                if (response.isSuccessful()) {
                    Log.i("ALAMKU", "SUCCESS GET DETAIL DATA ALAM = " + response.body().toString());
                    dataAlam = response.body().getData();
                    txt_title_tempat_alamku.setText(dataAlam.get(0).getTitle().toString());
                    txt_detail_nama_alam.setText(dataAlam.get(0).getTitle().toString());
                    txt_lokasi_alam.setText(dataAlam.get(0).getLocation().toString());
                    txt_created_at_alam.setText(dataAlam.get(0).getCreatedAt().toString());
                    txt_deskripsi_alamku.setText(dataAlam.get(0).getDescription().toString());
                    txt_rating_alamku.setText(dataAlam.get(0).getRate().toString());

                    Picasso.with(DetailAlamkuActivity.this).load(Constants.Apps.URL_IMAGE+dataAlam.get(0).getUrlImage().toString()).into(img_detail_alamku);

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
}
