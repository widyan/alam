package com.widyan.alamku;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.widyan.alamku.customs.CustomEditTextLatoRegular;
import com.widyan.alamku.customs.CustomTextViewLatoRegular;
import com.widyan.alamku.utils.Utils;

public class LoginActivity extends AppCompatActivity {

    CustomEditTextLatoRegular ediTxt_username, edtTxt_password;
    Button btn_login;
    LinearLayout btn_to_register;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        ediTxt_username = (CustomEditTextLatoRegular)findViewById(R.id.ediTxt_username);
        edtTxt_password = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_password);
        btn_login = (Button)findViewById(R.id.btn_login);
        btn_to_register = (LinearLayout)findViewById(R.id.btn_to_register);

        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startThisActivity(LoginActivity.this, AlamkuActivity.class);
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
