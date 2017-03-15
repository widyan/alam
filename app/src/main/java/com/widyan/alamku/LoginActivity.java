package com.widyan.alamku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.widyan.alamku.customs.CustomEditTextLatoRegular;
import com.widyan.alamku.dao.UserDao;
import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.models.User;
import com.widyan.alamku.utils.Constants;
import com.widyan.alamku.utils.Utils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.http.POST;

public class LoginActivity extends AppCompatActivity {

    CustomEditTextLatoRegular ediTxt_username, edtTxt_password;
    Button btn_login;
    LinearLayout btn_to_register;
    private APIServices mAPIService;
    private UserDao userDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ediTxt_username = (CustomEditTextLatoRegular)findViewById(R.id.ediTxt_username);
        edtTxt_password = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_to_register = (LinearLayout)findViewById(R.id.btn_to_register);

        mAPIService = Utils.getAPIService();
        userDao = new UserDao(mAPIService);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Log.i("ALAMKU","username = " + ediTxt_username.getText().toString());
                Log.i("ALAMKU","pass = " + edtTxt_password.getText().toString());
                Log.i("ALAMKU","md5 pass = " + Utils.md5(edtTxt_password.getText().toString()));
                userDao.login(ediTxt_username.getText().toString(), Utils.md5(edtTxt_password.getText().toString()));
                //login(ediTxt_username.getText().toString(), Utils.md5(edtTxt_password.getText().toString()));
                //Utils.startThisActivity(LoginActivity.this, AlamkuActivity.class);
            }
        });
        btn_to_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startThisActivity(LoginActivity.this, SignUpActivity.class);
            }
        });
    }


}
