package com.univerisity.rentify;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.univerisity.Common.utils.Activities;
import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOAccess;
import com.univerisity.Domain.services.AccessService;

public class LoginActivity extends AppCompatActivity {

    private EditText txt_email;
    private EditText txt_password;
    private CheckBox cbx_remember;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        txt_email = findViewById(R.id.txt_login_email);
        txt_password = findViewById(R.id.txt_login_password);
        cbx_remember = findViewById(R.id.cbx_login_remember);

        //Si hay credeciales guardadas
        SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
        int state = preferences.getInt("state", 0);
        if (state == 1){
            txt_email.setText(preferences.getString("correo", ""));
            txt_password.setText(preferences.getString("password", ""));
            cbx_remember.setChecked(true);
        }
    }

    //Events
    public void btnLogIn_Click(View view){
        login();
    }

    public void btnForgotPassword_Click(View view){
        Intent intent = new Intent(this, ForgotPassword.class);
        startActivity(intent);
    }

    public void btnRedirectSignIn_Click(View view){
        Intent intent = new Intent(this, SigninActivity.class);
        startActivity(intent);
        finish();
    }

    //Methods
    private void login(){
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();

        DAOAccess access = new DAOAccess();
        AccessService service = new AccessService(access);

        service.login(email, password, new DAOAccess.LoginCallback() {
            @Override
            public void onLoginSuccess() {

                SharedPreferences preferences = getSharedPreferences("datos", Context.MODE_PRIVATE);
                SharedPreferences.Editor preferencesEdit = preferences.edit();

                if(cbx_remember.isChecked()){
                    preferencesEdit.putString("correo", email);
                    preferencesEdit.putString("password", password);
                    preferencesEdit.putInt("state", 1);
                }
                else{
                    preferencesEdit.remove("correo");
                    preferencesEdit.remove("password");
                    preferencesEdit.remove("state");
                }

                preferencesEdit.apply();

                startActivity(new Intent(LoginActivity.this, Activities.getMainActivity().getClass()));
            }

            @Override
            public void onLoginFailure(String errorMessage) {
                Toast.makeText(LoginActivity.this, errorMessage, Toast.LENGTH_LONG).show();
            }
        });

    }
}