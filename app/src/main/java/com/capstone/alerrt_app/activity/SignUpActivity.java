package com.capstone.alerrt_app.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.capstone.alerrt_app.R;
import com.capstone.alerrt_app.classes.EndPoints;
import com.capstone.alerrt_app.classes.ErrorHandler;
import com.capstone.alerrt_app.classes.Validation;
import com.capstone.alerrt_app.classes.beans.UserBean;
import com.capstone.alerrt_app.classes.requests.MySingleton;
import com.capstone.alerrt_app.classes.requests.MyStringRequest;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class SignUpActivity extends AppCompatActivity {

    @InjectView(R.id.frmRegisterAccount_txtName) EditText frmRegisterAccount_txtName;
    @InjectView(R.id.frmRegisterAccount_txtEmail) EditText frmRegisterAccount_txtEmail;
    @InjectView(R.id.frmRegisterAccount_txtPassword) EditText frmRegisterAccount_txtPassword;
    @InjectView(R.id.frmRegisterAccount_txtAddress) EditText frmRegisterAccount_txtAddress;
    @InjectView(R.id.frmRegisterAccount_txtBirthdate) EditText frmRegisterAccount_txtBirthdate;
    @InjectView(R.id.frmRegisterAccount_txtMobileNumber) EditText frmRegisterAccount_txtMobileNumber;
    @InjectView(R.id.frmRegisterAccount_radioGender) RadioGroup frmRegisterAccount_radioGender;
    @InjectView(R.id.frmRegisterAccount_btnSignUp) Button frmRegisterAccount_btnSignUp;
    @InjectView(R.id.btnGoToLoginPage) TextView btnGoToLoginPage;

    UserBean user = new UserBean();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        ButterKnife.inject(this);

        final EditText[] editTexts = new EditText[]{
                frmRegisterAccount_txtName,
                frmRegisterAccount_txtEmail,
                frmRegisterAccount_txtPassword,
                frmRegisterAccount_txtAddress,
                frmRegisterAccount_txtBirthdate,
                frmRegisterAccount_txtMobileNumber
        };

        frmRegisterAccount_btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!Validation.validateEditText(editTexts)){
                    final ProgressDialog progressDialog = new ProgressDialog(SignUpActivity.this);
                    progressDialog.setIndeterminate(true);
                    progressDialog.setMessage("Creating your account... Please wait!");
                    progressDialog.setCancelable(false);
                    progressDialog.show();

                    new android.os.Handler().postDelayed(
                            new Runnable() {
                                public void run() {
                                    registerUser();
                                    progressDialog.dismiss();
                                }
                            }, 3000);
                }
            }
        });

        btnGoToLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SignUpActivity.this, LoginActivity.class));
            }
        });
    }

    private void init(){
        try {
            int selectedGenderId = frmRegisterAccount_radioGender.getCheckedRadioButtonId();
            RadioButton genderRadioButton = findViewById(selectedGenderId);
            String gender = genderRadioButton.getText().toString();

            user.setFullname(frmRegisterAccount_txtName.getText().toString());
            user.setEmailAddress(frmRegisterAccount_txtEmail.getText().toString());
            user.setPassword(frmRegisterAccount_txtPassword.getText().toString());
            user.setAddress(frmRegisterAccount_txtAddress.getText().toString());
            user.setBirthdate(frmRegisterAccount_txtBirthdate.getText().toString());
            user.setMobileNumber(frmRegisterAccount_txtMobileNumber.getText().toString());
            user.setGender(gender);

            SimpleDateFormat sdf = new SimpleDateFormat("MMMM dd, yyyy | hh:mm a");
            Calendar dateAndTimeRegistered_calendar = Calendar.getInstance();

            user.setDateAndTimeRegistered(sdf.format(dateAndTimeRegistered_calendar.getTime()));
        }catch(Exception ex){
            Log.e("Error Message", ex.toString());
        }
    }

    public void registerUser(){
        try{
            init();

            Map<String, String> parameters = new HashMap<>();

            parameters.put("fullname",user.getFullname());
            parameters.put("emailAddress",user.getEmailAddress());
            parameters.put("password",user.getPassword());
            parameters.put("address",user.getAddress());
            parameters.put("birthdate",user.getBirthdate());
            parameters.put("mobileNumber",user.getMobileNumber());
            parameters.put("gender",user.getGender());

            parameters.put("dateAndTimeRegistered",user.getDateAndTimeRegistered());

            MyStringRequest request = new MyStringRequest(parameters, Request.Method.POST, EndPoints.REGISTER_USER,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Toast.makeText(SignUpActivity.this, response, Toast.LENGTH_LONG).show();
                            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                            startActivity(intent);
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    ErrorHandler.getInstance(getApplicationContext()).viewToastErrorMessage(getApplicationContext(),error.toString());
                }
            }
            );

            MySingleton.getInstance(SignUpActivity.this).addToRequestQueue(request);
        }catch(Exception ex){
            ErrorHandler.getInstance(this).viewToastErrorMessage(this,ex.toString());
        }
    }
}
