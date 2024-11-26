package com.univerisity.rentify;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.univerisity.Common.utils.Message;
import com.univerisity.DataAccess.DAOs.DAOAccess;
import com.univerisity.Domain.services.AccessService;

public class SigninActivity extends AppCompatActivity {

    private EditText txt_name;
    private  EditText txt_lastname;
    private EditText txt_phone;
    private EditText txt_email;
    private EditText txt_password;
    private EditText txt_password_repeat;
    private CheckBox cbx_terms_conditions;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        txt_name = findViewById(R.id.txt_signin_name);
        txt_lastname = findViewById(R.id.txt_signin_lastname);
        txt_phone = findViewById(R.id.txt_signin_phone);
        txt_email = findViewById(R.id.txt_signin_email);
        txt_password = findViewById(R.id.txt_signin_password);
        txt_password_repeat = findViewById(R.id.txt_signin_r_password);
        cbx_terms_conditions = findViewById(R.id.cbx_signin_tyc);
    }

    public void btnSignIn_Click(View view){
        signin();
    }

    public void btnRedirctLogIn_Click(View view){
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void signin(){

        if(!cbx_terms_conditions.isChecked()){
            Toast.makeText(this, "Acepte los terminos y condiciones", Toast.LENGTH_LONG).show();
            return;
        }

        String name = txt_name.getText().toString();
        String lastname = txt_lastname.getText().toString();
        String phone = txt_phone.getText().toString();
        String email = txt_email.getText().toString();
        String password = txt_password.getText().toString();
        String passwordRepeat = txt_password_repeat.getText().toString();

        if(error(name, lastname, email, phone, password, passwordRepeat)){

            if(!password.equals(passwordRepeat)){
                Toast.makeText(this, "Las contraseñas no coinciden", Toast.LENGTH_LONG).show();
                return;
            }

            DAOAccess access = new DAOAccess();
            AccessService service = new AccessService(access);

            service.signin(name, lastname, phone, email, password, new DAOAccess.LoginCallback() {
                @Override
                public void onLoginSuccess() {
                    Toast.makeText(SigninActivity.this, "Registrado", Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SigninActivity.this, LoginActivity.class));
                    finish();
                }

                @Override
                public void onLoginFailure(String errorMessage) {
                    Toast.makeText(SigninActivity.this, Message.Error, Toast.LENGTH_LONG).show();
                }
            });
        }
        else{
            Toast.makeText(this, "Campos incompletos", Toast.LENGTH_LONG).show();
        }
    }

    private boolean error(String name, String lastname, String email, String phone, String password, String repeat){

        boolean ret = true;

        if(name.isEmpty()){
            txt_name.setHintTextColor(Color.RED);
            txt_name.setHint("Nombre invalido");
            ret = false;
        }

        if(lastname.isEmpty()){
            txt_name.setHintTextColor(Color.RED);
            txt_name.setHint("Apellido invalido");
            ret = false;
        }

        if(email.isEmpty()){
            txt_name.setHintTextColor(Color.RED);
            txt_name.setHint("Correo invalido");
            ret = false;
        }

        if(password.isEmpty()){
            txt_name.setHintTextColor(Color.RED);
            txt_name.setHint("Contraseña invalida");
            ret = false;
        }

        if(repeat.isEmpty()){
            txt_name.setHintTextColor(Color.RED);
            txt_name.setHint("Contraeña invalida");
            ret = false;
        }

        return ret;
    }
}