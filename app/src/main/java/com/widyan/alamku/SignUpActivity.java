package com.widyan.alamku;

import android.app.DatePickerDialog;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.widyan.alamku.customs.CustomEditTextLatoRegular;
import com.widyan.alamku.customs.CustomTextViewLatoRegular;
import com.widyan.alamku.dao.UserDao;
import com.widyan.alamku.interfaces.api.APIServices;
import com.widyan.alamku.utils.Utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class SignUpActivity extends AppCompatActivity {

    CustomEditTextLatoRegular ediTxt_firstname, ediTxt_lastname, edtTxt_username, edtTxt_password, edtTxt_phone;
    CustomTextViewLatoRegular edtTxt_tgl_lahir;
    Button btn_register;
    LinearLayout btn_to_login;
    private APIServices mAPIService;
    private UserDao userDao;
    Calendar myCalendar = Calendar.getInstance();
    RadioGroup radio_group;
    RadioButton radio_button, radio_lakilaki, radio_perempuan;
    String jenis_kelamin;
    int radio_button_check;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        ediTxt_firstname = (CustomEditTextLatoRegular)findViewById(R.id.ediTxt_firstname);
        ediTxt_lastname = (CustomEditTextLatoRegular)findViewById(R.id.ediTxt_lastname);
        edtTxt_tgl_lahir = (CustomTextViewLatoRegular) findViewById(R.id.edtTxt_tgl_lahir);
        edtTxt_username = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_username);
        edtTxt_password = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_password);
        edtTxt_phone = (CustomEditTextLatoRegular)findViewById(R.id.edtTxt_phone);
        btn_register = (Button)findViewById(R.id.btn_register);
        btn_to_login = (LinearLayout)findViewById(R.id.btn_to_login);
        radio_group = (RadioGroup)findViewById(R.id.radio_group);
        radio_lakilaki = (RadioButton)findViewById(R.id.radio_lakilaki);
        radio_perempuan = (RadioButton)findViewById(R.id.radio_perempuan);

        mAPIService = Utils.getAPIService();
        userDao = new UserDao(SignUpActivity.this, mAPIService);

        edtTxt_tgl_lahir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new DatePickerDialog(SignUpActivity.this, date, myCalendar
                        .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                radio_button_check = radio_group.getCheckedRadioButtonId();
                radio_button = (RadioButton)findViewById(radio_button_check);
                jenis_kelamin = radio_button.getText().toString();

                Log.i("ALAMKU","jenis kelamin = " + jenis_kelamin);

                if(validation()){
                    userDao.register(ediTxt_firstname.getText().toString(), ediTxt_lastname.getText().toString(),
                            edtTxt_username.getText().toString(), edtTxt_password.getText().toString(),
                            edtTxt_tgl_lahir.getText().toString(),jenis_kelamin,edtTxt_phone.getText().toString());
                }

                //Utils.startThisActivity(SignUpActivity.this, LoginActivity.class);
            }
        });

        btn_to_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Utils.startThisActivity(SignUpActivity.this, LoginActivity.class);
                finish();
            }
        });
    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {
            // TODO Auto-generated method stub
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }

    };

    private void updateLabel() {

        String myFormat = "dd-MM-yyyy"; //In which you need put here
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.US);

        edtTxt_tgl_lahir.setText(sdf.format(myCalendar.getTime()));
    }

    public boolean validation(){
        boolean valid_tgl_lahir = false;
        boolean valid_username = Utils.validationInput(edtTxt_username, "username harus diisi");
        boolean valid_password = Utils.validationInput(edtTxt_password, "password harus diisi");
        boolean valid_firstname = Utils.validationInput(ediTxt_firstname, "firstname harus diisi");
        boolean valid_lastname = Utils.validationInput(ediTxt_lastname, "lastname harus diisi");
        boolean valid_phone = Utils.validationInput(edtTxt_phone, "phone harus diisi");
        if(edtTxt_tgl_lahir.getText().equals("") || edtTxt_tgl_lahir.getText() == null){
            valid_tgl_lahir =false;
        }else {
            valid_tgl_lahir = true;
        }

        if(valid_username && valid_password && valid_firstname && valid_lastname && valid_phone && valid_tgl_lahir){
            return true;
        }else {
            return false;
        }
    }
}
