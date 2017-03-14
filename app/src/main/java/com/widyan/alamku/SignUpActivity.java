package com.widyan.alamku;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import com.widyan.alamku.customs.CustomEditTextLatoRegular;
import com.widyan.alamku.customs.CustomTextViewLatoRegular;
import com.widyan.alamku.utils.Utils;

public class SignUpActivity extends AppCompatActivity {

    CustomEditTextLatoRegular ediTxt_firstname, ediTxt_lastname, edtTxt_tgl_lahir, edtTxt_username, edtTxt_password, edtTxt_phone;
    Button btn_register;
    LinearLayout btn_to_login;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ediTxt_firstname = (CustomEditTextLatoRegular)findViewById(R.id.ediTxt_firstname);
        ediTxt_lastname = (CustomEditTextLatoRegular)findViewById(R.id.ediTxt_lastname);
        edtTxt_tgl_lahir = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_tgl_lahir);
        edtTxt_username = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_username);
        edtTxt_password = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_password);
        edtTxt_phone = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_phone);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_to_login = (LinearLayout)findViewById(R.id.btn_to_login);

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startThisActivity(SignUpActivity.this, LoginActivity.class);
            }
        });

        btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startThisActivity(SignUpActivity.this, LoginActivity.class);
            }
        });
    }
}
