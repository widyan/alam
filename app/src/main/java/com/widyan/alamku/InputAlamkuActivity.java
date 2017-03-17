package com.widyan.alamku;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.widyan.alamku.customs.CustomEditTextLatoRegular;
import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.models.AlamData;
import com.widyan.alamku.models.User;
import com.widyan.alamku.utils.Constants;
import com.widyan.alamku.utils.SharedPrefData;
import com.widyan.alamku.utils.Utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InputAlamkuActivity extends AppCompatActivity {

    Spinner spinner_kategori_tempat;
    ImageView img_upload_alamku;
    CustomEditTextLatoRegular edtTxt_judul_tempat, edtTxt_lokasi_tempat, edtTxt_deskripsi_tempat;
    Button btn_upload;
    private APIServices mAPIService;
    private File file;
    private Uri selectedImage;
    String idUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_alamku);

        mAPIService = Utils.getAPIServiceGenerator();
        try {
            idUser = SharedPrefData.GetSaveDataUser(this).getIdUser().toString();
        }catch (Exception e){
            e.printStackTrace();
        }

        spinner_kategori_tempat = (Spinner)findViewById(R.id.spinner_kategori_tempat);
        img_upload_alamku = (ImageView) findViewById(R.id.img_upload_alamku);
        edtTxt_deskripsi_tempat = (CustomEditTextLatoRegular) findViewById(R.id.edtTxt_deskripsi_tempat);
        edtTxt_lokasi_tempat = (CustomEditTextLatoRegular) findViewById(R.id.edtTxt_lokasi_tempat);
        edtTxt_judul_tempat = (CustomEditTextLatoRegular) findViewById(R.id.edtTxt_judul_tempat);
        btn_upload = (Button) findViewById(R.id.btn_upload);

        img_upload_alamku.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Select Image"), Constants.KEY.PICK_IMAGE);
            }
        });

        btn_upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validation()){
                    addDataAlam(edtTxt_judul_tempat.getText().toString(), edtTxt_lokasi_tempat.getText().toString(),
                            spinner_kategori_tempat.getSelectedItem().toString(), edtTxt_deskripsi_tempat.getText().toString(), idUser, file, selectedImage);
                }
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constants.KEY.PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            selectedImage = data.getData();
            img_upload_alamku.setImageURI(selectedImage);

            Bitmap b = null;
            try {
                b = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                b.compress(Bitmap.CompressFormat.PNG, 90, outStream);
                String path = MediaStore.Images.Media.insertImage(getContentResolver(), b, "Alamku", null);
                selectedImage = Uri.parse(path);
            } catch (IOException e) {
                e.printStackTrace();
            }

            String[] filePathColumn = {MediaStore.Images.Media.DATA};
            android.database.Cursor cursor = getContentResolver().query(selectedImage, filePathColumn, null, null, null);
            if (cursor == null)
                return;

            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String filePath = cursor.getString(columnIndex);
            cursor.close();

            file = new File(filePath);
        }
    }

    public void addDataAlam(String judul, String location, String kategori, String deskripsi, String id_user, File fileimage, Uri fileUri) {
        Log.i("ALAMKU", "URL UPLOAD = " + Constants.Apps.ADD_TEMPAT_WISATA);
        RequestBody requestFile = RequestBody.create(MediaType.parse(getContentResolver().getType(fileUri)), fileimage);

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part image = MultipartBody.Part.createFormData("image", fileimage.getName(), requestFile);

        // add another part within the multipart request
        RequestBody req_judul = RequestBody.create(okhttp3.MultipartBody.FORM, judul);
        RequestBody req_location = RequestBody.create(okhttp3.MultipartBody.FORM, location);
        RequestBody req_kategori = RequestBody.create(okhttp3.MultipartBody.FORM, kategori);
        RequestBody req_deskripsi = RequestBody.create(okhttp3.MultipartBody.FORM, deskripsi);
        RequestBody req_id_user = RequestBody.create(okhttp3.MultipartBody.FORM, id_user);

        mAPIService.addDataAlam(req_judul, req_location,
                req_kategori, req_deskripsi,
                req_id_user, image).enqueue(new Callback<AlamData>() {
            @Override
            public void onResponse(Call<AlamData> call, Response<AlamData> response) {
                Log.i("ALAMKU", "SUCCESS UPLOAD " + response.toString());
                if (response.isSuccessful()) {
                    Log.i("ALAMKU", "SUCCESS UPLOADED");
                    Utils.startThisActivity(InputAlamkuActivity.this, AlamkuActivity.class);
                    finish();
                    //Toast.makeText(InputAlamkuActivity.this, "SUCCESS UPLOADED " + response.body().getTitle(), Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<AlamData> call, Throwable t) {
                t.printStackTrace();
            }
        });
    }

    public boolean validation(){
        boolean valid_judul = Utils.validationInput(edtTxt_judul_tempat, "judul harus diisi");
        boolean valid_lokasi = Utils.validationInput(edtTxt_lokasi_tempat, "lokasi harus diisi");
        boolean valid_deskripsi = Utils.validationInput(edtTxt_deskripsi_tempat, "deskripsi harus diisi");
        boolean valid_file = false;
        try {
            if(selectedImage == null){
                valid_file = false;
            }else {
                valid_file = true;
            }
        }catch (Exception e){
            Log.e("ALAMKU", "ERR InputAlamkuActivity = " + e.getMessage());
        }

        if(valid_judul && valid_lokasi && valid_deskripsi && valid_file){
            return true;
        }else {
            return false;
        }
    }
}
